<template>
  <div>
      <v-container>
          <div class="row"> 
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <v-col>
                      <h4 class="mb-5">제목</h4>
                      <v-text-field
                        label=""
                        solo
                        v-model="title"
                      ></v-text-field>
                      <h4 class="mb-5">내용</h4>
                      <v-textarea
                      label=""
                      solo
                      v-model="description"
                    ></v-textarea>
                    <v-select
                      v-model="category"
                      :items="categories"
                      label="category"
                      outlined
                    ></v-select>

                     <v-row class="align-center">
                      <h5>상품 상태</h5>
                      <v-btn-toggle
                        v-model="grade"
                        tile
                        color="deep-purple accent-3"
                        group>
                        <v-btn value="S">S</v-btn>
                        <v-btn value="A">A</v-btn>
                        <v-btn value="B">B</v-btn>
                        <v-btn value="C">C</v-btn>
                      </v-btn-toggle>
                    </v-row>

                    <v-row class="mb-5 align-center">
                      <v-text-field
                        label="위치"
                        v-model="location"
                        class="mr-4"
                      ></v-text-field>
                      <v-radio-group
                        v-model="direct"
                        mandatory>
                        <v-radio
                          label="택배"
                          value="0"
                        ></v-radio>
                        <v-radio
                          label="직거래"
                          value="1"
                        ></v-radio>
                        <v-radio
                          label="택배 & 직거래"
                          value="2"
                        ></v-radio>
                      </v-radio-group>
                    </v-row>
                    <v-row>
                      <v-text-field
                        label="경매시작가"
                        filled
                        outlined
                        :rules=[rules.price]
                        v-model="startPrice"
                      ></v-text-field>
                      <v-text-field
                        label="즉시구매가"
                        filled
                        outlined
                        :rules=[rules.price]
                        v-model="happyPrice"
                      ></v-text-field>
                    </v-row>

                    <v-row>
                      <v-menu
                        ref="startDateCalender"
                        v-model="startDateCalender"
                        :close-on-content-click="false"
                        :return-value.sync="startDateTime"
                        transition="scale-transition"
                        offset-y
                        min-width="auto">
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="startDateTime"
                            label="경매시작일"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="startDate"
                          no-title
                          scrollable
                          :min="todayDate"
                          :max="endDate"
                          >
                          <v-spacer></v-spacer>
                          <v-btn
                            text
                            color="primary"
                            @click="startDateCalender = false">
                            Cancel
                          </v-btn>
                           <v-dialog
                            ref="startTimeDialog"
                            v-model="startTimeDialog"
                            :return-value.sync="startTime"
                            persistent
                            width="290px">
                            <template v-slot:activator="{ on }">
                              <v-btn
                                text
                                color="primary"
                                v-on="on"
                              >TIME</v-btn>
                            </template>
                            <v-time-picker
                              v-if="startTimeDialog"
                              v-model="startTime"
                              full-width>
                              <v-spacer></v-spacer>
                              <v-btn
                                text
                                color="primary"
                                @click="startTimeDialog = false"
                              >Cancel</v-btn>
                              <v-btn
                                text
                                color="primary"
                                @click="$refs.startTimeDialog.save(startTime)"
                              >OK</v-btn>
                            </v-time-picker>
                          </v-dialog>
                          <v-btn
                            text
                            color="primary"
                            @click="$refs.startDateCalender.save(startDate + ' ' + startTime);">
                            OK
                          </v-btn>
                        </v-date-picker>
                      </v-menu>

                      <v-menu
                        ref="endDateCalender"
                        v-model="endDateCalender"
                        :close-on-content-click="false"
                        :return-value.sync="endDateTime"
                        transition="scale-transition"
                        offset-y
                        min-width="auto">
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="endDateTime"
                            label="경매종료일"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="endDate"
                          no-title
                          scrollable
                          :min="this.startDate"
                          >
                          <v-spacer></v-spacer>
                          <v-btn
                            text
                            color="primary"
                            @click="endDateCalender = false">
                            Cancel
                          </v-btn>
                           <v-dialog
                            ref="endTimeDialog"
                            v-model="endTimeDialog"
                            :return-value.sync="endTime"
                            persistent
                            width="290px">
                            <template v-slot:activator="{ on }">
                              <v-btn
                                text
                                color="primary"
                                v-on="on"
                              >TIME</v-btn>
                            </template>
                            <v-time-picker
                              v-if="endTimeDialog"
                              v-model="endTime"
                              full-width>
                              <v-spacer></v-spacer>
                              <v-btn
                                text
                                color="primary"
                                @click="endTimeDialog = false"
                              >Cancel</v-btn>
                              <v-btn
                                text
                                color="primary"
                                @click="endTimeCheck()"
                              >OK</v-btn>
                            </v-time-picker>
                          </v-dialog>
                          <v-btn
                            text
                            color="primary"
                            @click="$refs.endDateCalender.save(endDate + ' ' + endTime);">
                            OK
                          </v-btn>
                        </v-date-picker>
                      </v-menu>
                    </v-row>

                  </v-col>
                  <div class="shadow mt-5">
                    <div style="border: 1px solid #dddddd">
                      <div class="room-deal-information-title">사진 등록</div>
                      <div class="room-picture-notice">
                        <ul>
                          <li>사진은 가로로 찍은 사진을 권장합니다 (가로사이즈 최소 800px)</li>
                          <li>사진 용량은 사진 한장당 10MB까지 등록이 가능합니다.</li>
                        </ul>
                      </div>
                      <div class="room-file-upload-wrapper">
                        <div v-if="!files.length" class="room-file-upload-example-container">
                        <div>
                          <div class="text-center">이미지</div>
                          <div class="room-file-notice-item">
                            실 사진 최소 3장 이상 등록하셔야 하며. 가로사진을 권장합니다.
                          </div>
                          <div class="room-file-notice-item" style="color: #ef4351;">
                            로고를 제외한 불필요한 정보(워터마크, 상호, 전화번호 등)가 있는
                            매물은 비공개 처리됩니다
                          </div>
                          <div class="room-file-notice-item">
                            <div class="image-box">
                              <label for="file">일반 사진 등록</label>
                              <input type="file" id="file" ref="files" @change="imageUpload" multiple>
                            </div>
                          </div>
                        </div>
                        </div>
                        <div v-else style="height: 100%;">
                          <div class="file-preview-container">
                            <div v-for="(file, index) in files"
                            :key="index"
                            class="file-preview-wrapper">
                            <div class="file-close-button" @click="fileDeleteButton" :name="file.number"
                            >x
                            </div>
                            <img :src="file.preview"/>
                            </div>
                            <div class="file-preview-wrapper-upload">
                              <div class="image-box">
                                <label for="file">추가 사진 등록</label>
                                <input 
                                type="file"
                                id="file"
                                ref="files"
                                @change="imageAddUpload"
                                multiple
                                />
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <v-btn @click="writePost" block color="primary" class="mt-5 w-100">
                    작성하기
                  </v-btn>
                
                  <v-btn @click="testButton" block color="white" class="mt-5 w-100">
                    테스트하기
                  </v-btn>
                </div>
          </div>
      </v-container>
    </div>
</template>
<script lang="ts">
import Vue from 'vue';
import { itemApi } from "../utils/axios";
import moment from 'moment'

export default Vue.extend({
    name: "PostView",
    data: () => ({
        uid:"",
        title: "",
        description: "",
        category: "",
        location: "",
        startPrice: "",
        happyPrice: "",
        grade: "",
        direct: "",
        startDate: "",
        endDate: "",
        files: [],
        filesPreview: [],
        uploadImageIndex: 0,

        categories: ["의류", "전자기기"],

        startDateCalender: false,
        startTime: "",
        startTimeDialog: false,

        endDateCalender: false,
        endTime: "",
        endTimeDialog: false,

        todayDate: "",
        todayTime: "",

        startDateTime: "",
        endDateTime: "",
        rules: {
           price: (v:any) => !!(v || '').match(/^[1-9][0-9]*$/) ||
          '잘못된 입력입니다. 가격을 입력해주세요.'
        },
    }),
    mounted() {
      const today = moment();
    
      this.startDate = this.todayDate = today.format('YYYY-MM-DD');
      this.startTime = this.todayTime = today.format('HH:mm');
      this.startDateTime = this.startDate + ' ' + this.startTime;
    
      this.uid = this.$store.state.userModule.user.object.user.nickname;
    },
    methods: {
        async writePost(){
          const {
            uid,
            title, 
            description,
            category,
            location,
            startPrice,
            happyPrice,
            grade,
            direct,
            startDateTime,
            endDateTime, 
            files, 
            } = this;

            if (!title) alert('제목을 입력해주세요.');
            else if (!description) alert('내용을 입력해주세요.');
            else if (!category) alert('카테고리를 선택해주세요.');
            else if (!location) alert('위치를 입력해주세요.');
            else if (!startPrice) alert('경매시작가를 입력해주세요.');
            else if (!happyPrice) alert('즉시구매가를 입력해주세요.');
            else if (!startPrice.match(/^[1-9][0-9]*$/)) alert('경매시작가를 다시 입력해주세요.');
            else if (!happyPrice.match(/^[1-9][0-9]*$/)) alert('즉시구매가를 다시 입력해주세요.');
            else if (Number(startPrice) >= Number(happyPrice)) alert('경매시작가는 즉시구매가보다 작아야합니다.');
            else if (!grade) alert('상품등급을 입력해주세요.');
            else if (!endDateTime) alert('경매종료일을 입력해주세요.');
            else if (!files) alert('사진을 입력해주세요.');
            else {
              const formData = new FormData();

              formData.append("title", title);
              formData.append("description", description);
              formData.append("category", category);
              formData.append("location", location);
              formData.append("startPrice", startPrice);
              formData.append("happyPrice", happyPrice);
              formData.append("grade", grade);
              formData.append("direct", direct);
              formData.append("startDateTime", startDateTime);
              formData.append("endDateTime", endDateTime);
              
              // user id, image
              formData.append("uid", uid);

              files.forEach(el => {
                formData.append("files", (el as any).file)
              });

              const {data} = await itemApi.item(formData);
              
              // state true?
              console.log(data);
              if (data.status){
                alert("업로드가 완료되었습니다.");
                this.$router.push("/");
              } else {
                alert("업로드에 실패하였습니다.");
              }
            }
        },
      
      imageUpload(){
        console.log(this.$refs.files);
        console.log((this.$refs.files as any).files);

        // 배열을 생성하는데
        // 실제파일을 관리하는 부분, 이미지 preview를 관리하는 부분, index까지 관리하는 배열을 제작
        let num = -1;
        for (let i = 0; i < (this.$refs.files as any).files.length; i++) {
          (this.files as any) = [
            ...this.files,
            {
              // 실제 파일
              file: (this.$refs.files as any).files[i],
              // 이미지 프리뷰
              preview: URL.createObjectURL((this.$refs.files as any).files[i]),
              number: i
            }
          ];
          num = i;
        }
        this.uploadImageIndex = num + 1;
        //console.log(this.files);
      },

      imageAddUpload(){
        console.log(this.$refs.files);
        console.log((this.$refs.files as any).files);
        // 배열을 생성하는데
        // 실제파일을 관리하는 부분, 이미지 preview를 관리하는 부분, index까지 관리하는 배열을 제작
        let num = -1;
        for (let i = 0; i < (this.$refs.files as any).files.length; i++) {
          (this.files as any) = [
            ...this.files,
            {
              // 실제 파일
              file: (this.$refs.files as any).files[i],
              // 이미지 프리뷰
              preview: URL.createObjectURL((this.$refs.files as any).files[i]),
              number: i + this.uploadImageIndex
            }
          ];
          num = i;
        }
        this.uploadImageIndex = this.uploadImageIndex + num + 1;
        console.log(this.files);      
      },

      fileDeleteButton(e: any){
        const name = e.target.getAttribute("name");
        this.files = this.files.filter(data => (data as any).number !== Number(name));
      },

      startTimeCheck() {
         const today = moment();
         this.todayDate = today.format('YYYY-MM-DD');
         this.todayTime = today.format("HH:mm");

        if (this.startDate === this.endDate && this.startTime >= this.endTime)
          alert('시간이 잘못 입력되었습니다.');
        else if (this.todayDate === this.startDate && this.startTime <= this.todayTime)
          (this.$refs.startTimeDialog as any).save(this.todayTime);
        else (this.$refs.startTimeDialog as any).save(this.startTime);
      },

      endTimeCheck() {
        if (this.startDate === this.endDate && this.startTime >= this.endTime) {
          alert('시간이 잘못 입력되었습니다.');
        }
        else (this.$refs.endTimeDialog as any).save(this.endTime);
      },

      testButton() {
        console.log('-----------TEST-----------')
        console.log('변수명:타입')
        // uid
        console.log('uid:string')
        console.log(this.uid)
        // title
        console.log('title:string')
        console.log(this.title)
        // description,
        console.log('description:string')
        console.log(this.description)
        // category
        console.log('category:string')
        console.log(this.category)        
        // location
        console.log('location:string')
        console.log(this.location)        
        // startPrice
        console.log('startPrice:string')
        console.log(this.startPrice)      
        // happyPrice
        console.log('happyPrice:string')
        console.log(this.happyPrice) 
        // grade
        console.log('grade:string')
        console.log(this.grade)        
        // direct
        console.log('direct:string')
        console.log(this.direct)        
        // startDate
        console.log('startDateTime:string')
        console.log(this.startDateTime)        
        // endDate 
        console.log('endDateTime:string')
        console.log(this.endDateTime)        
        // files
        console.log('files:file')
        console.log(this.files)
        console.log('--------------------------')
      },
    },
  });

</script>

<style>
  .room-deal-information-title {
    text-align: center;
    font-size: 18px;
    line-height: 60px;
    border-bottom: 1px solid #dddddd;
  }

  .room-picture-notice {
    margin: 20px;
    padding: 20px 40px;
    border: 1px solid #dddddd;
  }

  .room-file-upload-wrapper {
    margin: 20px;
    border: 1px solid #dddddd;
    background-color: #f4f4f4;
    min-height: 350px;
    font-size: 15px;
    color: #888888;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  .room-file-upload-example-container {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 100%;
  }

  .room-file-notice-item {
    margin-top: 5px;
    text-align: center;
  }

  .image-box {
    margin-top: 30px;
    padding-bottom: 20px;
    text-align: center;
  }

  .file-preview-container {
    height: 100%;
    display: flex;
    flex-wrap: wrap;
  }

  .file-close-button {
    position: absolute;
    line-height: 18px;
    z-index: 99;
    font-size: 18px;
    right: 5px;
    top: 10px;
    color: #fff;
    font-weight: bold;
    background-color: #666666;
    width: 20px;
    height: 20px;
    text-align: center;
    cursor: pointer;
  }

  .file-preview-wrapper-upload {
    margin: 10px;
    padding-top: 20px;
    background-color: #888888;
    width: 190px;
    height: 130px;
  }

  .file-preview-container {
    height: 100%;
    display: flex;
    flex-wrap: wrap;
  }

  .file-preview-wrapper {
    padding: 10px;
    position: relative;
  }

  .file-preview-wrapper > img {
    position: relative;
    width: 190px;
    height: 130px;
    z-index: 10;
  }

  .image-box input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.image-box label {
  display: inline-block;
  padding: 10px 20px;
  background-color: #232d4a;
  color: #fff;
  vertical-align: middle;
  font-size: 15px;
  cursor: pointer;
  border-radius: 5px;
}

</style>