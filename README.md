# 1.Response
##### status
```
{
  status: "ok"
}
 
{
  status: "unlogin"(登录过期)
}
 
{
  status: "error"(服务器错误/恶意操作)
}
```

# 2.User
##### 数据表
```
{
    userId;
    subId;//所属分站id
    nickname;
    avatar;
    studentCard;
    grade;
    major;
    password;
    type;
    phone;
    wechart;
    introduce;
    title;
    works;
    orderTimes;
    orderedTimes;
    score;
}
```
##### 添加用户
```
{
  url: /api/user/add,
  method: post,
  content-type: application/json; charset=utf-8,
  data: User@object
}
```

##### 上传学生证照片
```
{
  url: /api/user/studentCard/upload,
  method: post,
  data: {
    image:
  }
}
```

##### 该用户是否是行家
```
request
{
  url: /api/user/isMaster,
  method: get
}
```

##### 登录
```
request
{
  url: /api/user/login,
  method: post,
  content-type: application/json; charset=utf-8,
  data: {
    nickname: ,
    password: base64加密后的密码
  }
}
{
  status: "error"(账号或密码错误)
}

{
  status: "black"(黑名单)
}
```

##### 退出登录
```
{
  url: /api/user/logout,
  method: get,
  content-type: application/json; charset=utf-8
}
```

##### 获取某用户基本信息
```
{
  url: /api/user/info/{userId},
  method: get
}
```
##### 获取用户自己的基本信息
```
request
{
  url: /api/user/info,
  method: get
}

```
##### 修改资料
```
{
  url: /api/user/edit,
  method: post,
  content-type: application/json; charset=utf-8,
  data: User@object
}
```

##### 更新头像
```
{
  url: /api/user/avatar/upload,
  method: post,
  data: {
    image:
  }
}

```

##### 获取是否登录
```
{
  url: /api/user/status
  method: get
}
  
{
  result : 0: 未登录，1：已登录
}
```

##### 获取分站列表
```
{
  url: /api/user/sub/list
}
```


# 3.Skill
##### 数据表
```
{
    skillId;
    userId;
    subId;
    title;
    description;
    works;
    totleTime;
    totlePrice;
    tagId;
    score;
    orderTimes;
    orderedTimes;
}
```
##### 添加技能
```
request
{
  url: /api/skill/add,
  method: post,
  content-type: application/json; charset=utf-8,
  data: Skill@object
}
```
##### 删除技能
```
{
  url: /api/skill/delete/{skillId},
  method: get
}
```

##### 查看自己的技能列表
```
request
{
  url: /api/skill/list
  method: get
}
```

##### 查看某用户技能列表
```
request
{
  url: /api/skill/list/{userId}
  method: get
}
```

##### 查看某用户技能列表except
```
{
  url: /api/skill/list/{userId}/{exceptSkillId}
  method: get
}
```

##### 查看某个技能详细信息
```
{
  url: /api/skill/info/{skillId},
  method: get
}
```

##### 关键字搜索技能列表
```
{
  url: /api/skill/search/keyword/{subId}/{keyword},
  method: get
}
```

##### 标签搜索技能列表
```
{
  url: /api/skill/search/tag/{subId}/{tagId},
  method: get
}
```

##### 该分站全部技能列表（最新创建的技能列表）
```
{
  url: /api/skill/search/all/{subId},
}
```
##### 查看热门技能列表
```
{
  url: /api/skill/search/hotest/{subId},
}
```

##### 查看热门技能列表(加标签)
```
{
  url: /api/skill/search/hotest/{subId}/{tagId},
}
```

##### 查看用户的全部评论
```
{
  url: /api/user/comments
}
```

# 4.Order
##### 数据表
```
{
    orderId;
    fromUserId;
    toUserId;
    skillId;
    introduction;
    result;//0:待审核；1：同意；2：拒绝；3：已完成；4：取消；
    wechart;
    rejectReason;
    isCommented;//如果已完成，是否评论了0：否，1：是
}
```
##### 添加预约
```
request
{
  url: /api/order/add,
  method: post,
  data: Order@object
}
```
##### (发起者)取消预约
```
{
  url: /api/order/cancel/{orderId},
  method: get
}
```
##### (行家)拒绝预约
```
{
  url: /api/order/reject/{orderId},
  method: post,
  data: {
    rejectReason;
  }
}
```

##### (行家)同意预约
```
{
  url: /api/order/agree/{orderId},
  method: get
}
```

##### 完成预约
```
{
  url: /api/order/finish/{orderId},
  method: get
}
```
##### 删除预约
```
{
  url: /api/order/delete/{orderId},
  method: get
}
```

##### 获取自己的预约列表
```
{
  url: /api/order/list,
  method: get
}
```

##### 获取自己的预约列表(自己约别人)
```
{
  url: /api/order/from/list,
  method: get
}
```


##### 获取自己的预约列表(别人约自己)
```
{
  url: /api/order/to/list,
  method: get
}
```
##### 查看某个预约详细信息
```
{
  url: /api/order/info/{orderId},
  method: get
}
```

# 5.Comment
##### 数据表
```
{
    commentId;
    userId;
    skillId;
    content;
    score;
}
```
##### 对某个技能进行评论
```  
{
  url: /api/skill/comment/add,
  method: post,
  data: {
    content:,
    orderId;
    score;
  }
}
```
##### 查看某个技能的评论列表
```
{
  url: /api/skill/comments/{skillId},
  method: get
}

```


# 6.Apply
##### 数据表
```
{
    applyId;
    userId;
    title;
    introduce;
    works;
    result;//0:审核中； 1：通过； 2：拒绝；
    rejectReason;
    wechart;
    
}
```

##### 申请行家认证
```
{
  url: /api/apply/add,
  method: post,
  content-type: application/json; charset=utf-8,
  data: Apply@object
}
```

##### 查看自己最近一次的认证信息
```
{
  url: /api/apply/latest,
  method: get
}
```

##### 查看自己的认证申请列表
```
{
  url: /api/apply/list,
  method: get
}
```

##### 查看某个认证申请的详细信息
```
{
  url: /api/apply/info/{applyId},
  method: get
}
```

# 7.Tag
##### 数据表
```
{
  tagId;
  name;
}
```

# 8.Sub
##### 数据表
```
{
  subId;
  token;
}
```
##### 根据token获取分站subId
```
{
  url: '/api/user/subid/{token}'
  method: get
}
```


# 9.Admin
##### 数据表
```
{
adminId;
subId;
name;
password;
}
```

##### 登录
```
{
    url: /api/admin/login
    data: {
        email;
        password
    }
}
```

##### 登出
```
{
    url: /api/admin/logout
    data: {
        email;
        password
    }
}
```

##### 查看登录状态
```
{
    url: /api/admin/status
}
```

##### 查看管理员信息
```
{
    url: /api/admin/info
}
```

##### 获取用户列表
```
{
    url: /api/admin/user/list,
    method: get,
    data: {
    }
}
```

##### 冻结用户（删除）
```
{
  url: /api/admin/deleteuser/{userId},
  method: post,
  data:{
    deleteReason;
  }
}
```

##### 下架行家
```
{
  url: /api/admin/mastershelve/{userId},
  method: post,
  data:{
    shelveReason;
  }
}
```

##### 上架行家
```
{
  url: /api/admin/masterground/{userId},
}
```
##### 冻结
```
{
  url: /api/admin/deleteuser/{userId},
  method: post,
  data:{
    deleteReason;
  }
}
```


##### 恢复用户
```
{
  url: /api/admin/recoveruser/{userId},
  method: get
}
```

##### 批准申请
```
{
  url: /api/admin/proveapply/{applyId}
  method: get
}
```

##### 拒绝申请
```
{
  url: /api/admin/rejectapply;
  method: post;
  data: {
    applyId;
    rejectReason;
  }
}
```


##### 撤销拒绝
```
{
  url: /api/admin/canclereject/{applyId};
}
```

##### 获取某个用户全部信息
```
{
  url: /api/admin/userinfo/{userId};
  method: get;
}
```

##### 获取认证申请列表
```
{
  url: /api/admin/applylist
  method: post
  data:{
    result: 0:(审核中的申请列表) 1:（已通过的申请列表）2：（已拒绝的申请列表）
  }
}
```

##### 获取认证申请详情
```
{
  url: /api/admin/applyinfo/{applyId}
  method: get
}
```
##### 更改管理员密码
```
{
  url: /api/admin/editpassword
  method: post
  data:{
    oldPasswrod
    newPassword
  }
}
```
##### 获取分站url
```
{
  url: /api/admin/token
}
```
##### 修改用户资料
```
{
  url: /api/admin/edit/{userId},
  method: post,
  content-type: application/json; charset=utf-8,
}
```

##### 更新用户头像
```
{
  url: /api/admin/avatar/upload/{userId},
  method: post,
  data: {
    image:
  }
}

```
# 11.statistic
##### 获取各个表的统计 {increment：增长值，sum：总量}
```
{
  url: /api/statistic/{type} (type="user","skill","order","master","apply")
  data: {
    startDate: 20160203@int
    endDate
  }
}
```
##### 获取各个表的记录的当前数量
```
{
  url: /api/statistic/count
}
```


# 10.OpenSub
##### 数据表
```
{
  email;
  mpName;
  mpNum;
  schoolName;
  wechat;
}
```
##### 添加开通分站申请
```
{
  url："/api/opensub/add",
  method: post
}
```


# 11.File
##### 用户头像图片
```
{
  url："/qujingfile/useravatar/..."
}
```
##### 用户头像图片
```
{
  url："/qujingfile/studentcard/..."
}
```

# 12.Root

##### 登录
```
{
  url: /api/root/login
  method: post
  data: {
    name;
    password;
  }
}
```

##### 登出
```
{
  url: /api/root/logout
}
```

##### 获取分站列表
```
{
  url："/api/root/sub/list",
}
```

##### 获取分站详情
```
{
  url："/api/root/sub/info",
}
```

##### 获取平台信息概览
```
{
  url："/api/root/sys/info",
}
```


##### 关闭分站
```
{
  url："/api/root/closesub/{openSubId}",
  data: {
    closeReason;
  }
}
```
##### 启动分站
```
{
  url："/api/root/startsub/{openSubId}"
}
```
##### 同意开通分站申请
```
{
  url："/api/root/approveopen/{openSubId}",
}
```
##### 拒绝开通分站申请
```
{
  url："/api/root/rejectopen/{openSubId}",
}
```
##### 获取开通分站申请列表
```
{
  url："/api/root/opensub/list",
  type:'post'
  data:{
    status;
    currentPage;
    pageSize;
  }
}
```
