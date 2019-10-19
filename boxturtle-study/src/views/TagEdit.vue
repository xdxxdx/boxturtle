<template>
  <el-form ref="form" :model="tag" label-width="80px" @submit.native.prevent="onSubmit">
    <el-form-item label="标签名称">
      <el-input v-model="tag.tagName"></el-input>
    </el-form-item>
    <el-form-item label="优先级">
      <el-input-number v-model="tag.priority" :min="0" label="优先级"></el-input-number>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" native-type="submit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template> 

<script>
export default {
  data() {
    return {
      tag: {
        tagName: "",
        priority: ""
      }
    };
  },
  created() {
    this.fetch();
  },
  methods: {
    fetch() {
      console.log(this.$route.params.tagName);
      // this.$http.get(`/tag/detail/${this.$route.params.tagName}`).then(res=>{
      //   console.log(res.data);
      // })
       this.$http.get('/tag/detail',{data:{'tagName':this.$route.params.tagName}}).then(res=>{
        this.tag=res.data
      })
    },
    onSubmit() {
      this.$http.post("/tag/edit", this.tag).then(res => {
        console.log(res);
        if (res.data.msg == "修改成功") {
          this.$message({
            message: "恭喜你，这是一条成功消息",
            type: "success"
          });
          this.$router.push("/tag/list");
        }
      });
    }
  }
};
</script>