<template>
  <div>
      <v-container>
          <div class="row">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="text-center">hihihi</div>
                </div>
          </div>
      </v-container>
  </div>
</template>
<script lang="ts">
import Vue from 'vue';
import { postApi } from "../utils/axios";

export default Vue.extend({
    name: "Product",
    data: () => ({
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
    }),
    methods: {
        async writePost(){
        const { 
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
          formData.append("location", location);
          formData.append("startPrice", startPrice);
          formData.append("happyPrice", happyPrice);
          formData.append("grade", grade);
          formData.append("direct", direct);
          formData.append("startDate", startDate);
          formData.append("endDate", endDate);
         
          const {data} = await postApi.post(formData);
          console.log(data);
          if (data.upload){
            alert("업로드가 완료되었습니다.");
            this.$router.push("/");
          } else {
            alert("업로드에 실패하였습니다.");
          }
      },
    }
  });
</script>