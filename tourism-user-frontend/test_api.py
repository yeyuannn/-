#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
旅游系统接口自动化测试工具
"""

import requests
import json
import time
from datetime import datetime

BASE_URL = "http://localhost:8080"

class APITester:
    def __init__(self):
        self.results = []
        self.token = None
        self.pass_count = 0
        self.fail_count = 0
        
    def log_result(self, case_id, name, method, url, response, expected, actual):
        status = "PASS" if expected == actual else "FAIL"
        if status == "PASS":
            self.pass_count += 1
        else:
            self.fail_count += 1
            
        result = {
            "case_id": case_id,
            "name": name,
            "method": method,
            "url": url,
            "status": status,
            "expected": expected,
            "actual": actual,
            "time": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        }
        self.results.append(result)
        print(f"[{status}] {case_id}: {name}")
        
    def test_user_login_success(self):
        url = f"{BASE_URL}/user/login"
        data = {
            "username": "admin",
            "password": "123456"
        }
        try:
            response = requests.post(url, json=data, timeout=10)
            res_json = response.json()
            self.token = res_json.get("data", {}).get("token")
            actual_code = res_json.get("code")
            self.log_result(
                "API-001", "用户登录-成功",
                "POST", "/user/login",
                response.text, 200, actual_code
            )
        except Exception as e:
            self.log_result(
                "API-001", "用户登录-成功",
                "POST", "/user/login",
                str(e), 200, -1
            )
            
    def test_user_login_wrong_password(self):
        url = f"{BASE_URL}/user/login"
        data = {
            "username": "admin",
            "password": "wrongpassword"
        }
        try:
            response = requests.post(url, json=data, timeout=10)
            res_json = response.json()
            actual_code = res_json.get("code")
            self.log_result(
                "API-002", "用户登录-密码错误",
                "POST", "/user/login",
                response.text, 401, actual_code
            )
        except Exception as e:
            self.log_result(
                "API-002", "用户登录-密码错误",
                "POST", "/user/login",
                str(e), 401, -1
            )
    
    def test_get_attractions_list(self):
        url = f"{BASE_URL}/sysAttractions/list"
        params = {"page": 1, "pageSize": 10}
        try:
            response = requests.get(url, params=params, timeout=10)
            res_json = response.json()
            actual_code = res_json.get("code")
            self.log_result(
                "API-004", "获取景点列表",
                "GET", "/sysAttractions/list",
                response.text, 200, actual_code
            )
        except Exception as e:
            self.log_result(
                "API-004", "获取景点列表",
                "GET", "/sysAttractions/list",
                str(e), 200, -1
            )
    
    def test_get_hotel_list(self):
        url = f"{BASE_URL}/sysHotel/list"
        params = {"page": 1, "pageSize": 10}
        try:
            response = requests.get(url, params=params, timeout=10)
            res_json = response.json()
            actual_code = res_json.get("code")
            self.log_result(
                "API-009", "获取酒店列表",
                "GET", "/sysHotel/list",
                response.text, 200, actual_code
            )
        except Exception as e:
            self.log_result(
                "API-009", "获取酒店列表",
                "GET", "/sysHotel/list",
                str(e), 200, -1
            )
    
    def test_get_forum_list(self):
        url = f"{BASE_URL}/sysForum/list"
        params = {"page": 1, "pageSize": 10}
        try:
            response = requests.get(url, params=params, timeout=10)
            res_json = response.json()
            actual_code = res_json.get("code")
            self.log_result(
                "API-010", "获取论坛列表",
                "GET", "/sysForum/list",
                response.text, 200, actual_code
            )
        except Exception as e:
            self.log_result(
                "API-010", "获取论坛列表",
                "GET", "/sysForum/list",
                str(e), 200, -1
            )
    
    def test_create_order_without_token(self):
        url = f"{BASE_URL}/sysAttractionOrder/add"
        data = {
            "attractionsId": "1",
            "time": "2024-12-31",
            "num": 2
        }
        try:
            response = requests.post(url, json=data, timeout=10)
            actual_code = response.status_code
            self.log_result(
                "API-008", "创建订单-无Token",
                "POST", "/sysAttractionOrder/add",
                response.text, 401 if actual_code == 401 else 401, actual_code
            )
        except Exception as e:
            self.log_result(
                "API-008", "创建订单-无Token",
                "POST", "/sysAttractionOrder/add",
                str(e), 401, -1
            )
    
    def performance_test(self, url, method="GET", concurrent=10):
        """简易性能测试"""
        print(f"\n=== 性能测试: {url} 并发数: {concurrent} ===")
        start_time = time.time()
        success = 0
        for i in range(concurrent):
            try:
                if method == "GET":
                    response = requests.get(url, timeout=10)
                else:
                    response = requests.post(url, timeout=10)
                if response.status_code == 200:
                    success += 1
            except:
                pass
        end_time = time.time()
        avg_time = (end_time - start_time) / concurrent
        print(f"总耗时: {end_time - start_time:.2f}s")
        print(f"平均响应时间: {avg_time:.2f}s")
        print(f"成功率: {success/concurrent*100:.1f}%")
        
    def generate_report(self):
        print("\n" + "="*50)
        print("测试报告")
        print("="*50)
        print(f"测试时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
        print(f"总用例数: {len(self.results)}")
        print(f"通过: {self.pass_count}")
        print(f"失败: {self.fail_count}")
        print(f"通过率: {self.pass_count/len(self.results)*100 if self.results else 0:.1f}%")
        print("="*50)
        
        with open("测试报告.txt", "w", encoding="utf-8") as f:
            f.write("旅游系统接口测试报告\n")
            f.write("="*50 + "\n")
            f.write(f"测试时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"总用例数: {len(self.results)}\n")
            f.write(f"通过: {self.pass_count}\n")
            f.write(f"失败: {self.fail_count}\n")
            f.write(f"通过率: {self.pass_count/len(self.results)*100 if self.results else 0:.1f}%\n")
            f.write("\n详细结果:\n")
            for r in self.results:
                f.write(f"\n{r['case_id']} {r['name']} - {r['status']}\n")
                f.write(f"  预期: {r['expected']}, 实际: {r['actual']}\n")
        
        print("测试报告已保存到: 测试报告.txt")
        
    def run_all_tests(self):
        print("开始执行接口自动化测试...")
        print(f"测试地址: {BASE_URL}")
        print("-"*50)
        
        self.test_user_login_success()
        self.test_user_login_wrong_password()
        self.test_get_attractions_list()
        self.test_get_hotel_list()
        self.test_get_forum_list()
        self.test_create_order_without_token()
        
        self.generate_report()
        
        print("\n=== 性能测试演示 ===")
        self.performance_test(f"{BASE_URL}/sysAttractions/list", concurrent=10)

if __name__ == "__main__":
    tester = APITester()
    tester.run_all_tests()
