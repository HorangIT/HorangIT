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
                      :items="items"
                      label="category"
                      outlined
                    ></v-select>
                    <v-row class="mb-5">
                      <v-text-field
                        label="위치"
                        v-model="location"
                      ></v-text-field>
                      <v-checkbox
                        v-model="direct"
                        label="직거래 여부"
                      ></v-checkbox>
                    </v-row>
                    <v-row>
                      <v-text-field
                        label="경매시작가"
                        filled
                        outlined
                        v-model="startPrice"
                      ></v-text-field>
                      <v-text-field
                        label="즉시구매가"
                        filled
                        outlined
                        v-model="happyPrice"
                      ></v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field
                        label="경매시작일"
                        v-model="startDate"
                      ></v-text-field>
                       <v-text-field
                        label="경매마감일"
                        v-model="endDate"
                      ></v-text-field>
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
                </div>
          </div>
      </v-container>
  </div>
</template>
<script lang="ts">
import Vue from 'vue';
import { postApi } from "../utils/axios";

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

        items: ["의류", "전자기기"],
    }),
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
          startDate,
          endDate, 
          files, 
          } = this;
          const formData = new FormData();
          formData.append("title", title);
          formData.append("description", description);
          formData.append("category", category);
          formData.append("location", location);
          formData.append("startPrice", startPrice);
          formData.append("happyPrice", happyPrice);
          formData.append("grade", grade);
          formData.append("direct", direct);
          formData.append("startDate", startDate);
          formData.append("endDate", endDate);
          
          // user id, image
          formData.append("uid", uid);

          files.forEach(el => {
            formData.append("files", el.file)
          });

          const {data} = await postApi.post(formData);
          
          // state true?
          console.log(data);
          if (data.state){
            alert("업로드가 완료되었습니다.");
            this.$router.push("/");
          } else {
            alert("업로드에 실패하였습니다.");
          }
      },
      
      imageUpload(){
        console.log(this.$refs.files);
        console.log(this.$refs.files.files);
        // 배열을 생성하는데
        // 실제파일을 관리하는 부분, 이미지 preview를 관리하는 부분, index까지 관리하는 배열을 제작
        let num = -1;
        for (let i = 0; i < this.$refs.files.files.length; i++) {
          this.files = [
            ...this.files,
            {
              // 실제 파일
              file: this.$refs.files.files[i],
              // 이미지 프리뷰
              preview: URL.createObjectURL(this.$refs.files.files[i]),
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
        console.log(this.$refs.files.files);
        // 배열을 생성하는데
        // 실제파일을 관리하는 부분, 이미지 preview를 관리하는 부분, index까지 관리하는 배열을 제작
        let num = -1;
        for (let i = 0; i < this.$refs.files.files.length; i++) {
          this.files = [
            ...this.files,
            {
              // 실제 파일
              file: this.$refs.files.files[i],
              // 이미지 프리뷰
              preview: URL.createObjectURL(this.$refs.files.files[i]),
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
        this.files = this.files.filter(data => data.number !== Number(name));
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
    /* align-items: center; */
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