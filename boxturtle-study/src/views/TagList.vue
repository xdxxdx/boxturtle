 <template>
  <el-table :data="tagList" style="width: 40%">
    <el-table-column prop="tagName" label="标签名称" width="180"></el-table-column>
    <el-table-column prop="priority" label="优先级" width="180"></el-table-column>
    <el-table-column fixed="right" label="操作" width="100">
      <template slot-scope="scope">
        <el-button @click="remove(scope.row.tagName)" type="text" size="small">删除</el-button>
        <el-button @click="edit(scope.row.tagName)" type="text" size="small">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

  <script>
export default {
  data() {
    return {
      tagList: []
    };
  },
  methods: {
    fetch() {
      this.$http.get("/tag/list").then(res => {
        this.tagList = res.data;
      });
    },
    remove(tagName) {
      console.log(tagName);
      this.$http
        .delete("/tag/remove", { data: { tagName: tagName } })
        .then(res => {
          if (res.data.msg == "删除成功") {
              this.fetch();
          }
        });
    },
    edit(tagName) {
      this.$router.push(`/tag/${tagName}/edit`);
    }
  },
  created() {
    this.fetch();
  }
};
</script>