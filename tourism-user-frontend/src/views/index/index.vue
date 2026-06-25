<template>
  <div class="index">
    <headers></headers>
    <div class="search-section">
      <el-carousel height="500px">
        <el-carousel-item v-for="(item,index) in rotations" :key="index">
          <img style="width:100%;height:100%" :src="item.image.includes('http') ? item.image : ($store.state.HOST + item.image)">
        </el-carousel-item>
      </el-carousel>
      <div class="search-container">
        <div class="search-title">探索研学目的地</div>
        <el-autocomplete
          v-model="searchKeyword"
          :fetch-suggestions="querySearch"
          placeholder="请输入研学地点名称"
          size="large"
          class="search-input"
          @select="handleSelect"
          clearable
        >
          <template slot-scope="{ item }">
            <div class="search-item" @click="goToDetail(item.id)">
              <i class="el-icon-location-outline"></i>
              <span>{{ item.name }}</span>
            </div>
          </template>
        </el-autocomplete>
        <el-button type="primary" size="large" class="search-btn" @click="doSearch">
          <i class="el-icon-search"></i> 搜索
        </el-button>
      </div>
    </div>
    <div class="index1">
      <div class="index2">
        <div class="index3">
          推荐研学地点
        </div>
        <div class="index4">
          <div class="index5" v-for="(item,index) in attractions" :key="index" >
            <img style="width:100%;height:220px" :src="item.images.split(',')[0]">
            <div class="index6">
              <div class="index7">{{item.name}}</div>
              <div class="index8">{{item.introduce}}</div>
            </div>
            <div class="index9" style="margin-top:10px" @click="toInfo(item.id)">
                预 约
              </div>
          </div>
        </div>
        <el-button style="margin-top:10px" size="small" type="primary" plain @click="toAttraction">查看更多</el-button>
      </div>
    </div>
    <div class="index10">
    </div>
    <div class="index11">
      全年总接待研学人数：{{count}}
    </div>
    <div class="index1">
      <div class="index2">
        <div class="index3">
          研学路线
        </div>
        <div class="index4">
          <div class="index5" v-for="(item,index) in line">
            <img style="width:100%;height:220px" :src="item.images.split(',')[0]">
            <div class="index6">
              <div class="index7">{{item.name}}</div>
              <div class="index8">{{item.introduce}}</div>
              <div class="index9" @click="toLineInfo(item.id)">
                查 看
              </div>
            </div>
          </div>
        </div>
        <el-button style="margin-top:10px" size="small" type="primary" plain @click="toLine">查看更多</el-button>
      </div>
    </div>
    <bottoms></bottoms>
  </div>
</template>

<script>
  import {getSysRotationsList,getSysAttractionsIndex,getUserCount,getSysLineIndex,getSysAttractionsList} from '../../api/api'
  import headers from '@/components/header'
  import bottoms from '@/components/bottom'
  export default {
    data() {
      return{
        rotations: [],
        attractions: [],
        count: 0,
        line: [],
        searchKeyword: '',
        allAttractions: [],
      }
    },
    components: {
      headers,
      bottoms
    },
    methods: {
      toAttraction() {
        this.$router.push("/attractions")
      },
      toLine() {
        this.$router.push("/line")
      },
      toInfo(id) {
        this.$router.push("/attractionsInfo?id=" + id)
      },
      toLineInfo(id) {
        this.$router.push("/lineInfo?id=" + id)
      },
      querySearch(queryString, cb) {
        let attractions = this.allAttractions
        let results = queryString
          ? attractions.filter(item => item.name.toLowerCase().includes(queryString.toLowerCase()))
          : attractions
        cb(results.slice(0, 10))
      },
      handleSelect(item) {
        this.goToDetail(item.id)
      },
      doSearch() {
        if (!this.searchKeyword.trim()) {
          this.$message.warning('请输入搜索关键词')
          return
        }
        const results = this.allAttractions.filter(item => 
          item.name.toLowerCase().includes(this.searchKeyword.toLowerCase())
        )
        if (results.length === 0) {
          this.$message.info('未找到相关研学地点')
        } else if (results.length === 1) {
          this.goToDetail(results[0].id)
        } else {
          this.$message.success(`找到 ${results.length} 个相关地点，跳转到列表页`)
          this.$router.push({
            path: "/attractions",
            query: { keyword: this.searchKeyword }
          })
        }
      },
      goToDetail(id) {
        this.$router.push("/attractionsInfo?id=" + id)
      },
      getSysRotationsList() {
        getSysRotationsList().then(res => {
          if (res.code == 1000) {
            this.rotations = res.data
          }
        })
      },
      getSysAttractionsIndex() {
        getSysAttractionsList().then(listRes => {
          if(listRes.code == 1000 && listRes.data) {
            this.allAttractions = listRes.data.filter(item => item.state === 1)
            this.attractions = this.allAttractions.slice(0, 6)
          }
        })
      },
      getUserCount() {
        getUserCount().then(res => {
          if (res.code == 1000) {
            this.count = res.data
          }
        })
      },
      getSysLineIndex() {
        getSysLineIndex().then(res => {
          if (res.code == 1000) {
            this.line = res.data
          }
        })
      }
    },
    created() {
     
    },
    mounted() {
      this.getSysRotationsList()
      this.getSysAttractionsIndex()
      this.getUserCount()
      this.getSysLineIndex()
    }
 }
</script>

<style scoped>
   @import url('../../assets/css/index.css');
</style>