# 1.Response
##### success
```javascript
{
  status: "ok"
}
```

##### error
```javascript
{
  status: "unlogin"(登录过期)
}
 
{
  status: "error"(服务器错误/恶意操作)
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
  status: "psdErr"(密码错误)
}
```

##### 退出登录
```javascript
request
{
  url: /api/user/logout,
  method: get,
  content-type: application/json; charset=utf-8
}
```

##### 获取某用户基本信息
```javascript
request
{
  url: /api/user/getUserById/{userId},
  method: get
}
 
response:User@object
```
##### 更新头像
```javascript
{
  url: /api/user/uploadAvatar,
  method: post,
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
  data: Skill@object
}
```
##### 删除技能
```javascript
{
  url: /api/skill/deleteSkill/{skillId},
  method: get
}
```

##### 修改技能
```javascript
{
  url: /api/skill/editSkill,
  method: post,
  content-type: application/json; charset=utf-8,
  data: Skill@object
}
```

##### 查看某用户技能列表
```javascript
request
{
  url: /api/skill/getSkills/{userId}
  method: get
}
 
response:SKill@object[]
```

##### 查看某个技能详细信息
```javascript
request
{
  url: /api/skill/getSkillDetail/{skillId},
  method: get
}
 
response:SKill@object
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
 
response:SKill@object[]
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
  url: /api/order/cancelOrder/{orderId},
  method: post
}
```

##### 获取自己的预约列表
```javascript
request
{
  url: /api/order/getOrders,
  method: get
}
 
response:Order@object[]
```
##### 查看某个预约详细信息
```javascript
request
{
  url: /api/order/getOrderDetail/{orderId},
  method: get
}
 
response:Order@object
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
  url: /api/comment/getCommentsBySkillId/{skillId},
  method: get
}
 
response:Comment@object[](按时间递增排序)
```

##### 获取某个技能的新评论
```javascript
request
{
  url: /api/comment/getCommentIM/{skillId},
  method: get
}
 
response:Comment@object[]
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
 
response:Apply@object[]
```

##### 查看某个认证申请的详细信息
```javascript
request
{
  url: /api/apply/getApplyDetail/{applyId},
  method: get
}
 
response:Apply@object
```