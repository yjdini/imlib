#0.Response

operate_success
```json
{
  status: "ok"
}
```

list_query_success
```json
{
  status: "ok",
  list: [],
  currentPage: 当前页号码(1 - page_max),
  recordCount: 当前搜索条件下的记录总数
}
```

detail_query_success
```json
{
  status: "ok",
  data:{
  
  } 
}
```

error
```json
{
  status: "err"
}
```

#1.User
#####添加/更新
```json
{
  url: /api/user/adduser,
  method: post,
  content-type: application/json; charset=utf-8,
  data: "{
    id: (若没有则添加，若有则为更新),
    name: 姓名,
    age: 年龄,
    sex: 性别(1为男，0为女),
    telephone: 电话,
    email: 邮箱,
    password: base64加密后的密码,
    type: 用户类型(字符型:'a'dmin为系统管理员，'c'ommon为普通用户.该参数只在添加时有效)  
  }"
}
添加用户时，没有带上的字段设置为初始值，不会报错。
更新用户信息时，没有带上的字段不会更改。
```
=======

<h3>1.user</h3>
a)添加/更新
<ul>
<li>
url : /api/user/adduser
</li>
<li>
method : post
</li>
<li>
Content-Type : application/json
</li>
</ul>
<table>
<caption>data</caption>
<tr>
<td>property</td>
<td>type</td>
<td></td>
</tr>
</table>

