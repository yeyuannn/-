<template>
  <div class="bottom">
    <div class="bottom-container">
      <div class="bottom-column bottom-logo">
        <div class="slogan">探索世界，发现美好</div>
      </div>
      
      <div class="bottom-column bottom-info">
        <h3 class="column-title">关于我们</h3>
        <p class="info-text">
          本研学管理系统为用户提供全方位的研学服务，包括研学地点查询预订、研学路线规划、住宿预订、资讯浏览等功能。
          我们致力于打造一个便捷、高效、安全的研学服务平台，让您的研学之旅更加轻松愉快。
        </p>
      </div>
      
      <div class="bottom-column bottom-contact">
        <h3 class="column-title">联系我们</h3>
        <div class="contact-item">
          <span class="contact-icon">📧</span>
          <span class="contact-text">xxx@mail.com</span>
        </div>
        <div class="contact-item">
          <span class="contact-icon">💬</span>
          <span class="contact-text">QQ: xxx</span>
        </div>
        <div class="contact-item">
          <span class="contact-icon">📱</span>
          <span class="contact-text">微信: xxx</span>
        </div>
        <div class="contact-item">
          <span class="contact-icon">🔗</span>
          <span class="contact-text">微博: xxx@mail.com</span>
        </div>
        <div class="contact-item">
          <span class="contact-icon">📞</span>
          <span class="contact-text">电话: xxx</span>
        </div>
      </div>
      
      <div class="bottom-column bottom-links">
        <h3 class="column-title">快速链接</h3>
        <ul class="link-list">
          <li @click="toPage('index')" class="link-item">首页</li>
          <li @click="toPage('attractions')" class="link-item">研学地点</li>
          <li @click="toPage('line')" class="link-item">研学路线</li>
          <li @click="toPage('hotel')" class="link-item">住宿预订</li>
          <li @click="toPage('forum')" class="link-item">研学论坛</li>
          <li @click="toPage('center')" class="link-item">个人中心</li>
        </ul>
      </div>
    </div>
    
    <div class="bottom-copyright">
      <div class="copyright-text">© 2025 大学生研学管理系统. 保留所有权利.</div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return{

      }
    },
    methods: {
      toPage(name) {
        if (name == "index") {
          this.$router.push("/")
        } else if (name == "vegetable") {
          this.$router.push("/vegetable")
        } else if (name == "information") {
          this.$router.push("/information")
        } else if (name == "shed") {
          this.$router.push("/shed")
        } else if (name == "leave") {
          this.$router.push("/leave")
        } else if (name == "manage") {
          window.open("http://localhost:3000")
        } else if (name == "car") {
          this.$router.push("/car")
        } else if (name == "favor") {
          this.$router.push("/favor")
        } else if (name == "order") {
          this.$router.push("/order")
        } else if (name == "appointment") {
          this.$router.push("/appointment")
        } else if (name == "attractions") {
          this.$router.push("/attractions")
        } else if (name == "line") {
          this.$router.push("/line")
        } else if (name == "hotel") {
          this.$router.push("/hotel")
        } else if (name == "forum") {
          this.$router.push("/forum")
        } else if (name == "center") {
          this.$router.push("/center")
        }
      }
    },
  }
</script>

<style scoped>
  .bottom {
    margin-top: 40px;
    width: 100%;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    font-family: 'Microsoft YaHei', sans-serif;
    padding: 40px 0 20px;
  }
  
  .bottom-container {
    display: flex;
    justify-content: space-between;
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
    flex-wrap: wrap;
    gap: 30px;
  }
  
  .bottom-column {
    flex: 1;
    min-width: 200px;
    padding: 20px 0;
  }
  
  .bottom-logo {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    padding: 20px;
    border-radius: 8px;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
  }
  
  .slogan {
    font-size: 20px;
    font-weight: 700;
    color: #fff;
    letter-spacing: 2px;
    line-height: 1.5;
  }
  
  .column-title {
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 20px;
    position: relative;
    padding-bottom: 10px;
  }
  
  .column-title::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 40px;
    height: 3px;
    background-color: #fff;
    border-radius: 2px;
  }
  
  .info-text {
    font-size: 14px;
    line-height: 1.8;
    color: rgba(255, 255, 255, 0.85);
    text-align: justify;
  }
  
  .contact-item {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    font-size: 14px;
  }
  
  .contact-icon {
    font-size: 18px;
    margin-right: 12px;
    width: 20px;
    text-align: center;
  }
  
  .contact-text {
    color: rgba(255, 255, 255, 0.85);
  }
  
  .link-list {
    list-style: none;
    padding: 0;
    margin: 0;
  }
  
  .link-item {
    margin-bottom: 12px;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.85);
    cursor: pointer;
    transition: all 0.3s ease;
    padding: 5px 0;
    position: relative;
    overflow: hidden;
  }
  
  .link-item:hover {
    color: #fff;
    padding-left: 10px;
  }
  
  .link-item::before {
    content: '';
    position: absolute;
    left: 0;
    bottom: 0;
    width: 0;
    height: 2px;
    background-color: #fff;
    transition: width 0.3s ease;
  }
  
  .link-item:hover::before {
    width: 100%;
  }
  
  .bottom-copyright {
    text-align: center;
    margin-top: 40px;
    padding-top: 20px;
    border-top: 1px solid rgba(255, 255, 255, 0.2);
    font-size: 13px;
    color: rgba(255, 255, 255, 0.7);
  }
  
  /* 响应式设计 */
  @media (max-width: 768px) {
    .bottom-container {
      flex-direction: column;
      align-items: center;
      text-align: center;
    }
    
    .bottom-column {
      margin-bottom: 30px;
    }
    
    .column-title::after {
      left: 50%;
      transform: translateX(-50%);
    }
    
    .contact-item {
      justify-content: center;
    }
    
    .bottom-logo {
      padding: 15px;
    }
    
    .slogan {
      font-size: 18px;
      letter-spacing: 1px;
    }
  }
</style>