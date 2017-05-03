# 1.Response
##### operate_success
```javascript
{
  status: "ok"
}
```
##### page_list_query_success
```javascript
{
  status: "ok",
  data: [],
  currentPage: 当前页号码(1 - page_max),
  recordCount: 当前搜索条件下的记录总数
}
```


##### detail_query_success
```javascript
{
  status: "ok",
  data:{

  } 
}
```

##### error
```javascript
{
  status: "unlogin"(登录过期)
}
 
{
  status: "error"(服务器错误)
}
```

# 2.User
##### 添加用户
```javascript
{
  url: /api/user/addUser,
  method: post,
  content-type: application/json; charset=utf-8,
  data: {
    name: 姓名,
    age: 年龄,
    sex: 性别(1为男，0为女),
    telephone: 电话,
    email: 邮箱,
    password: base64加密后的密码,
    type: 用户类型(字符型:'m'aster为行家用户，'c'ommon为普通用户.该参数只在添加时有效)  
  }
}
```
##### 修改资料
```javascript
{
  url: /api/user/editUser,
  method: post,
  content-type: application/json; charset=utf-8,
  data: User@object
}
```

##### 登录
```javascript
request
{
  url: /api/user/login,
  method: post,
  content-type: application/json; charset=utf-8,
  data: {
    phone: ,
    password: base64加密后的密码
  }
}
 
response
{
  status: "ok"
}
 
{
  status: "unRegist"(账号不存在)
}
 
{
  status: "error"(密码错误)
}
```

##### 退出登录
```javascript
request
{
  url: /api/user/logout,
  method: get,
  content-type: application/json; charset=utf-8,
  data: {
    
  }
}
```

##### 获取某用户基本信息
```javascript
request
{
  url: /api/user/getUserById,
  method: get,
  data: {
    id: 
  }
}
 
response
{
  status: "ok",
  data: User@object
}
```
##### 更新头像
```javascript
{
  url: /api/user/uploadAvatar,
  method: post,
  content-type: application/json; charset=utf-8,
  data: {
    image:
  }
}

```



# 3.Skill
##### 添加技能
```javascript
{
  url: /api/skill/addSkill,
  method: post,
  content-type: application/json; charset=utf-8,
  data: {
    Skill@object
  }
}
```
##### 删除技能
```javascript
{
  url: /api/skill/deleteSkill,
  method: get,
  content-type: application/json; charset=utf-8,
  data: {
    skillId:
  }
}
```

##### 修改技能
```javascript
{
  url: /api/skill/editSkill,
  method: post,
  content-type: application/json; charset=utf-8,
  data: {
    Skill@object
  }
}
```

##### 查看某用户技能列表
```javascript
request
{
  url: /api/skill/getSkills,
  method: get,
  data: {
    id:
  }
}
 
response
{
  status: "ok",
  data: SKill@object[]
}
```

##### 查看某个技能详细信息
```javascript
request
{
  url: /api/skill/getSkillDetail,
  method: get,
  data: {
    skillId:
  }
}
 
response
{
  status: "ok",
  data: SKill@object
}
```

##### 关键字搜索技能列表
```javascript
request
{
  url: /api/skill/search,
  method: post,
  data: {
    keyword:
  }
}
 
response
{
  status: "ok",
  data: SKill@object[]
}
```

# 4.Order
##### 添加预约
```javascript
{
  url: /api/order/addOrder,
  method: post,
  data: Order@object
}
```
##### 取消预约
```javascript
{
  url: /api/order/cancelOrder,
  method: post,
  data: {
    id: 
  }
}
```

##### 获取自己的预约列表
```javascript
request
{
  url: /api/order/getOrders,
  method: get
}
 
response
{
  status: "ok",
  data: Order@object[]
}
```
##### 查看某个预约详细信息
```javascript
request
{
  url: /api/order/getOrderDetail,
  method: get,
  data:{
    orderId:
  }
}
 
response
{
  status: "ok",
  data: Order@object
}
```

# 5.Comment
##### 对某个技能进行评论/回复某个评论
```javascript
request
{
  url: /api/comment/addComment,
  method: post,
  data: {
    skillId:,
    commentId:,
    content:
  }
}
```
##### 查看某个技能的评论列表
```javascript
request
{
  url: /api/comment/getCommentsBySkillId,
  method: get,
  data: {
    skillId: 
  }
}
 
response
{
  status: "ok",
  data: Comment@object[](按时间递增排序)
}
```

##### 获取某个技能的新评论
```javascript
request
{
  url: /api/comment/getCommentIM,
  method: get,
  data: {
    skillId: 
  }
}
 
response
{
  status: "ok",
  data: Comment@object[](按时间递增排序)
}
```

# 6.Apply
##### 申请行家认证
```javascript
{
  url: /api/apply/addApply,
  method: post,
  content-type: application/json; charset=utf-8,
  data: Apply@object
}
```

##### 查看自己的认证申请列表
```javascript
request
{
  url: /api/apply/getApplys,
  method: get
}
 
response
{
  status: "ok",
  list: Apply@object[]
}
```

##### 查看某个认证申请的详细信息
```javascript
request
{
  url: /api/apply/getApplyDetail,
  method: get,
  data: {
    applyId: 
  }
}
 
response
{
  status: "ok",
  data: Apply@object
}
```