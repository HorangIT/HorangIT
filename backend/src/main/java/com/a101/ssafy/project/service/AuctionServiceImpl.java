package com.a101.ssafy.project.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.redis.RedisUtil;
import com.a101.ssafy.project.repository.UserRepository;

@Service
public class AuctionServiceImpl implements AuctionService {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 맞출 포맷
    final String ITEM_NAME = "item"; //현재 가격 관리해주는애
    final String ITEM_EXPIRED = "Expired"; //만료
    final String ITEM_START_PRICE = "Start"; //시작가격
    final String ITEM_HAPPY_PRICE = "Happy"; //flex가격
    final String ITEM_SELLER_ID = "Seller"; //seller 확인

    final String AUCTION = "auction";
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserRepository userRepository;

    @Override
    public String getCurrentAuctionValue(String itemId) {
        String value = redisUtil.getData(ITEM_NAME + itemId);

        return value == null ? "null" : value;
    }

    @Override
    public String getCurrentExpiredValue(String itemId) {
        String value = redisUtil.getData(ITEM_EXPIRED + itemId);

        return value == null ? "null" : value;
    }

    @Override
    public long getAuctionUnit(String price) { //oldPrice: 응찰을 시도하려고 누른 프론트에서 받은 그가격!
        if ("0".equals(price)) { //집 같은거(비싼거)올려놓고 시작가 0원으로 한다고 해도 팔리면 본인손해니까 이렇게해도됨 ㅋㅋ 내손해아님
            return 100L;
        }

        char firstChar = price.charAt(0);
        int length = price.length() - 2;
        if (firstChar < '5') {
            double temp = Math.pow(10, length);

            return (long) temp;
        } else if ('5' <= firstChar) {
            double temp = Math.pow(10, length);

            return (long) (5 * temp);
        }

        return 10L;
    }

    @Override
    public JSONObject getPriceAfterAuction(String oldPrice, String itemId) {
        JSONObject jobj = new JSONObject();
        System.out.println(oldPrice + "옛날 가격");
        long happyPrice = Long.parseLong(redisUtil.getData(ITEM_HAPPY_PRICE + itemId));
        long newPrice = Long.parseLong(oldPrice) + getAuctionUnit(oldPrice);

        long nextPrice = newPrice + getAuctionUnit(newPrice + "");

        if (happyPrice <= Long.parseLong(oldPrice)) {
            jobj.put("test", "응찰 가격을 넘어섰어요! 이제 사야해요.");

            jobj.put("nowPrice", happyPrice);
            jobj.put("nextPrice", happyPrice);

            String str = getLastAuctionLog(itemId);
            if (str != null) {
                jobj.put("log", getLastAuctionLog(itemId)); //log까지 넣어줌
            }

            return jobj;

        }

        //happy price 보다 값이 커지는 경우
        if (happyPrice <= newPrice) {
            newPrice = happyPrice;
        }

        if (happyPrice <= nextPrice) {
            nextPrice = happyPrice;
        }
        redisUtil.setData(ITEM_NAME + itemId, newPrice + ""); //레디스 값 갱신

        String str = getLastAuctionLog(itemId);
        if (str != null) {
            jobj.put("log", getLastAuctionLog(itemId)); //log까지 넣어줌
        }
        jobj.put("nowPrice", newPrice);
        jobj.put("nextPrice", nextPrice);

        return jobj;
    }

    public void done(String itemId) {
        redisUtil.deleteData(ITEM_EXPIRED + itemId);
        redisUtil.deleteData(ITEM_HAPPY_PRICE + itemId);
    }

    @Override
    public List<String> getAuctionLog(String itemId) {
        return redisUtil.getAllLdata(AUCTION + itemId);
    }

    @Override
    public String getLastAuctionLog(String itemId) {
        List<String> str = redisUtil.getLastLdata(AUCTION + itemId);
        if (str == null) {
            return null;
        } else {
            return str.get(0);
        }
    }

    @Override
    public String getNicknameById(String userId) {


        return (String) redisUtil.getHdata("user", userId);
    }

    @Override
    public void addAuctionLog(String userId, String itemId, String nowPrice) {
        String nickname = getNicknameById(userId);

        Date date = java.util.Calendar.getInstance().getTime();

        redisUtil.setLdata(AUCTION + itemId, nickname + ";" + nowPrice + ";" + format.format(date) + ";" + userId); //닉네임 제약에 대해서 이야기를 좀 해봐야겠다.
    }

    @Override
    public JSONObject auction(String userId, String itemId) {
        if (userId.equals(redisUtil.getData(ITEM_SELLER_ID + itemId))) {
            JSONObject jobj = new JSONObject();
            jobj.put("error", "판매자가 경매할 수 없습니다.");
            return jobj;

        }
        String getCurrentPrice = getCurrentAuctionValue(itemId);
        addAuctionLog(userId, itemId, getCurrentPrice);

        JSONObject jobj = getPriceAfterAuction(getCurrentPrice, itemId);
        if (jobj.get("test") != null) { //test!=null 이면 test 즉 이제 응찰해야한다는뜻
            done(itemId);
        }
        return jobj;
    }

    @Override
    public JSONObject flex(String userId, String itemId) {
        redisUtil.setData(ITEM_NAME + itemId, redisUtil.getData(ITEM_HAPPY_PRICE + itemId));
        addAuctionLog(userId, itemId, redisUtil.getData(ITEM_NAME + itemId));
        JSONObject jobj = new JSONObject();
        System.out.println(redisUtil.getData(ITEM_NAME + itemId));
        jobj = getPriceAfterAuction(redisUtil.getData(ITEM_NAME + itemId), itemId);

        jobj.put("test", "응찰 가격을 넘어섰네요, 이제 사야해요 (FLEX!)");

        done(itemId);
        return jobj;
    }


}
