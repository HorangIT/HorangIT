package com.a101.ssafy.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.receipt.Receipt;
import com.a101.ssafy.project.model.receipt.ReceiptDto;
import com.a101.ssafy.project.redis.RedisUtil;
import com.a101.ssafy.project.repository.ItemRepository;
import com.a101.ssafy.project.repository.ReceiptRepository;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    final String ITEM_NAME = "item";
    final String ITEM_EXPIRED = "Expired";
    final String AUCTION = "auction";

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ItemRepository ItemRepository;

    @Autowired
    AuctionService auctionService;

    @Override
    public Receipt createReceipt(String itemId) {
        ReceiptDto receiptDto = new ReceiptDto();

        receiptDto.setItemId(Long.parseLong(itemId));
        String str = auctionService.getLastAuctionLog(itemId);

        Optional<Item> opt = ItemRepository.findById(Long.parseLong(itemId));
        if (opt.isPresent()) {
            Item item = opt.get();
            receiptDto.setSellerId(item.getUserId());
            receiptDto.setStatus(item.getStatus());
            receiptDto.setItemTitle(item.getName());
        }

        if (str == null) { //널인 경우는 아무도 이 물건을 살 생각을 안 했다는 뜻
            receiptDto.setBuyerId(-1L);
            receiptDto.setFinalPrice(-1L);
        } else { //널이 아닌 경우는 누군가 한 번이라도 낙찰을 시도했다는 뜻
            String[] temp = str.split(";"); //닉네임;가격;시간;아이디
            receiptDto.setBuyerId(Long.parseLong(temp[3]));
            receiptDto.setFinalPrice(Long.parseLong(temp[1]));
        }

        Receipt receipt = new Receipt();
        BeanUtils.copyProperties(receiptDto, receipt);

        receiptRepository.save(receipt);
        return receipt;

    }


    public Receipt setStatusByItemId(String itemId, int status) {
        Optional<Receipt> opt = receiptRepository.findByItemId(Long.parseLong(itemId));

        if (opt.isPresent()) {
            Receipt receipt = opt.get();
            receipt.setStatus(status);

            receiptRepository.save(receipt);

            return receipt;
        } else {
            return null;
        }
    }

    @Override
    public List<Receipt> getReceiptByBuyerId(long buyerId) {
        return receiptRepository.findAllByBuyerId(buyerId);
    }

    @Override
    public List<Receipt> getReceiptBySellerId(long sellerId) {
        return receiptRepository.findAllBySellerId(sellerId);
    }

}
