<template>
  <div class="index">
    <div class="titleDiv">
      <!--    头部数据-->
      <el-row :gutter="24" class="topInfo">

        <el-col :span="6">
          <div id="stuNumDiv" class="el-colDiv">
            <div id="ssv1-main-text" class="nowDiv">实时</div>
            <span class="title">用户数</span><br/>
            <span class="digital">{{ this.userCount }}</span><br/>
          </div>
        </el-col>

        <el-col :span="6">
          <div id="haveRoomDiv" class="el-colDiv">
            <div id="ssv1-main-text" class="nowDiv">实时</div>
            <span class="title">研学地点数</span><br/>
            <span class="digital">{{ this.attractionsCount }}</span><br/>
          </div>
        </el-col>

        <el-col :span="6">
          <div id="repairNum" class="el-colDiv">
            <span class="title">研学线路数</span><br/>
            <span class="digital">{{ this.lineCount }}</span><br/>
          </div>
        </el-col>

        <el-col :span="6">
          <div id="emptyRoom" class="el-colDiv">
            <span class="title">住宿数</span><br/>
            <span class="digital">{{ this.hotelCount }}</span><br/>
          </div>
        </el-col>

      </el-row>
    </div>
    <div class="index1" id="index1">

    </div>
    <div class="index1" id="index2">

    </div>

    <div class="index2" id="index3">

    </div>

    <div class="index2" id="index4">

    </div>

  </div>
</template>

<script>
import {getManageData, attractionOrderChart, hotelOrderChart, countChart} from '../../../api/api'
  import * as echarts from "echarts";
  export default {
    data() {
      return{
        myChart: null,
        myChart1: null,
        shuju: {},
        userCount: 0 ,
        attractionsCount: 0 ,
        lineCount: 0 ,
        hotelCount: 0 ,
      }
    },
    methods: {
      init() {
        countChart().then(res => {
          this.userCount = res.data.userCount;
          this.attractionsCount = res.data.attractionsCount;
          this.lineCount = res.data.lineCount;
          this.hotelCount = res.data.hotelCount;
        })


        hotelOrderChart().then(res => {

          if (res.code == 1000) {
            this.shuju = res.data
            var chartDom = document.getElementById('index4');
            this.myChart = echarts.init(chartDom);
            var option = {
              title: {
                text: '住宿预约人数统计'
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross',
                  label: {
                    backgroundColor: '#6a7985'
                  }
                }
              },
              legend: {
                data: ['预约数']
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: [
                {
                  type: 'category',
                  boundaryGap: false,
                  data: this.shuju.names
                }
              ],
              yAxis: [
                {
                  type: 'value'
                }
              ],
              series: [
                {
                  name: '预约数',
                  type: 'pie',
                  stack: 'Total',
                  label: {
                    show: true,
                    position: 'top'
                  },
                  areaStyle: {},
                  emphasis: {
                    focus: 'series'
                  },
                  data: this.shuju.nums
                }
              ]
            }
            this.myChart.setOption(option);

          }
        })
        attractionOrderChart().then(res => {

          if (res.code == 1000) {
            this.shuju = res.data
            var chartDom = document.getElementById('index3');
            this.myChart = echarts.init(chartDom);
            var option = {
              title: {
                text: '研学地点预约人数统计'
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross',
                  label: {
                    backgroundColor: '#6a7985'
                  }
                }
              },
              legend: {
                data: ['预约数']
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: [
                {
                  type: 'category',
                  boundaryGap: false,
                  data: this.shuju.names
                }
              ],
              yAxis: [
                {
                  type: 'value'
                }
              ],
              series: [
                {
                  name: '预约数',
                  type: 'bar',
                  stack: 'Total',
                  label: {
                    show: true,
                    position: 'top'
                  },
                  areaStyle: {},
                  emphasis: {
                    focus: 'series'
                  },
                  data: this.shuju.nums
                }
              ]
            }
            this.myChart.setOption(option);

          }
        })
        getManageData().then(res => {
          if (res.code == 1000) {
            this.shuju = res.data
            var chartDom = document.getElementById('index1');
            this.myChart = echarts.init(chartDom);
            var option = {
              title: {
                text: '近七日研学地点预约'
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross',
                  label: {
                    backgroundColor: '#6a7985'
                  }
                }
              },
              legend: {
                data: ['预约数']
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: [
                {
                  type: 'category',
                  boundaryGap: false,
                  data: this.shuju.dates
                }
              ],
              yAxis: [
                {
                  type: 'value'
                }
              ],
              series: [
                {
                  name: '预约数',
                  type: 'line',
                  stack: 'Total',
                  label: {
                    show: true,
                    position: 'top'
                  },
                  areaStyle: {},
                  emphasis: {
                    focus: 'series'
                  },
                  data: this.shuju.nums
                }
              ]
            }
            this.myChart.setOption(option);

            var chartDom1 = document.getElementById('index2');
            this.myChart1 = echarts.init(chartDom1);
            var option1 = {
              title: {
                text: '近七日住宿预约'
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross',
                  label: {
                    backgroundColor: '#6a7985'
                  }
                }
              },
              legend: {
                data: ['预约数']
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: [
                {
                  type: 'category',
                  boundaryGap: false,
                  data: this.shuju.dates
                }
              ],
              yAxis: [
                {
                  type: 'value'
                }
              ],
              series: [
                {
                  name: '预约数',
                  type: 'line',
                  stack: 'Total',
                  label: {
                    show: true,
                    position: 'top'
                  },
                  areaStyle: {},
                  emphasis: {
                    focus: 'series'
                  },
                  data: this.shuju.orders
                }
              ]
            }
            this.myChart1.setOption(option1);
          }
        })
      }
    },
    created() {

    },
    mounted() {
      this.init()
    }
 }
</script>

<style scoped>
  .index {
    width: 100%;
    height: 100%;
    font-family: '黑体';
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  .index1 {
    width: 49%;
    height: 49%;
    margin-bottom: 1%;
    background-color: #ffffff;
    box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  }
  .index2 {
    width: 49%;
    height: 49%;
    margin-bottom: 1%;
    background-color: #ffffff;
    box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  }
  .titleDiv {
    width: 100%;
    height: 30%;
    margin-bottom: 1%;
    background-color: #ffffff;
    box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  }
  .wv-lt-refresh {
    display: none;
  }

  .topInfo {
    margin: 0 auto;
  }

  .el-colDiv {
    margin: 20px auto;
    max-width: 340px;
    min-width: 200px;
    overflow: hidden;
    height: 115px;
    border-radius: 5px;
    background-color: black;
    color: white;
    padding-left: 15px;
    padding-top: 15px;
    position: relative;
  }

  .nowDiv {
    width: 38px;
    height: 19px;
    position: absolute;
    right: 5%;
    font-size: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 3px;
  }

  .digital {
    font-size: 45px;
    line-height: normal;
    margin-left: 20px;
    margin-top: 200px;
  }

  .title {
    font-size: 18px;
  }

  .last-span {
    font-size: 13px;
  }

  #stuNumDiv {
    background-color: #2e4057;
  }

  #ssv1-main-text {
    background-color: #1398ff;
  }

  #haveRoomDiv {
    background-color: #1398ff;
  }

  #ssv2-main-text {
    background-color: #2e4057;
  }

  #repairNum {
    background-color: #008789;
  }

  #ssv3-main-text {
    background-color: #ffb400;
  }

  #emptyRoom {
    background-color: #ffb400;
  }

  #ssv4-main-text {
    background-color: #008789;
  }
</style>
