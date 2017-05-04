package com.ini.controllers;

import java.lang.reflect.Field;
import java.util.*;
//import org.springframework.http.converter.HttpMessageNotWritableException;
import com.aop.annotation.Authentication;
import com.aop.authentication.AuthenticationType;
import com.ini.entity.UserRepository;
import com.utils.PrintUtil;
import org.apache.catalina.util.Introspection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.mongod.core.DataBaseFactory;
import com.db.mongod.core.MongoClientFactory;
import com.ini.entity.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/rest")
public class TestController
{
	@Autowired
	private MongoOperations mongoTemplate;

	@Resource
	private UserRepository userRepository;

//	@RequestMapping("/showUser/{id}/{name}")
//	public User showUser(@PathVariable ObjectId id, @PathVariable String name)
//	{
//		User user = new User();
//		user.setId(id);
//		user.setName(name);
//		return user;
//	}
//
	@RequestMapping("/getUser/{name}")
	@Authentication(AuthenticationType.User)
	public List<Document> getUser(@PathVariable String name)
	{
		MongoCollection<Document> userCollection = DataBaseFactory.getDataBase().getCollection("user");
		FindIterable<Document> fi = userCollection.find(new Document().append("name", name));//find(Bson filter) ------ find(Bson filter, Document.class)
		MongoCursor<Document> it = fi.iterator();
//		fi.into(result);
		
		List<Document> result = new ArrayList<Document>();
		
		while(it.hasNext())
		{
			Document document = it.next();
			result.add(document);
		}
		return result;
	}
	
	/**
	 * retrieve data and put it in the format of pojo.
	 */
	@RequestMapping("/getUserEntity/{name}")
	public List<User> getUserEntity(@PathVariable String name)
	{
		DB db = MongoClientFactory.getMongoClient().getDB("test");
		DBCollection userCollection = db.getCollection("user");
		DBCursor fi = userCollection.find(new BasicDBObject().append("name", name));
		Iterator<DBObject> it = fi.iterator();

		MongoOperations mongoTemplate = new MongoTemplate(MongoClientFactory.getMongoClient(),"test");
		MongoConverter convert = mongoTemplate.getConverter();
		
		List<User> re = new ArrayList<User>(); 
		while(it.hasNext())
		{
			DBObject dbObject = it.next();
			re.add(convert.read(User.class, dbObject));
		}
		return re;
	}
	
	@RequestMapping("/mongotemplate/test")
	public List<User> mongoTemplateTest()
	{
		MongoOperations mongoTemplate = new MongoTemplate(MongoClientFactory.getMongoClient(),"test");
		System.out.println(mongoTemplate.collectionExists(User.class));
		
		Query query = new Query().addCriteria(Criteria.where("name").is("hahaha"));
		mongoTemplate.find(query, User.class);
		List<User> ul = mongoTemplate.findAll(User.class);
		return ul;
	}

	@RequestMapping("/httptest")
	public void httptest(HttpServletRequest request, HttpServletResponse response)
	{
		//PrintUtil.print(request);

		Field[] fields = Introspection.getDeclaredFields(request.getClass());

		for (Field field : fields)
		{
			field.setAccessible(true);
			if("request".equals(field.getName()))
			{
				try {
					PrintUtil.print(field.get(request));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@RequestMapping("/setcookie/{key}/{value}")
	public ResponseEntity setcookie(HttpServletResponse response, @PathVariable String key, @PathVariable String value)
	{
		HttpHeaders headers = new HttpHeaders();
		StringBuilder cookieStr = new StringBuilder();
		cookieStr.append(key).append('=').append(value).append(';');
		cookieStr.append("path=/search;domain=localhost;");
		headers.put("Set-Cookie", Collections.singletonList(cookieStr.toString()));
		return new ResponseEntity(headers,HttpStatus.OK);
	}

	@RequestMapping("/getcookies")
	public Cookie[] getCookie(HttpServletRequest request, HttpServletResponse response)
	{
		List a = new ArrayList();
		a.getClass().isArray();
		if(request.getCookies() != null)
			PrintUtil.print(request.getCookies());
		return request.getCookies();
	}

	@RequestMapping("/getUser/idtest")
	public Document getUserByObjId()
	{
		MongoCollection<Document> userCollection = DataBaseFactory.getDataBase().getCollection("user");
		FindIterable<Document> fi = userCollection.find(new Document().append("_id", new ObjectId("585aa3798b7a627b59571e11")));
		MongoCursor<Document> it = fi.iterator();

		while(it.hasNext())
		{
			return it.next();
		}
		return null;
	}
	
	@RequestMapping("/addUser/{name}")
	public ResponseEntity<?> addUserDatabase(@PathVariable String name)
	{
		MongoDatabase database = DataBaseFactory.getDataBase();
		MongoCollection<Document> userCollection = database.getCollection("user");
		Document document = new Document().append("name", name);
		userCollection.insertOne(document);
		return new ResponseEntity<ObjectId>(document.getObjectId("_id"), HttpStatus.OK);
//		return new ResponseEntity<MongoDatabase>(database ,HttpStatus.OK);
		//getObjectId() = (ObjectId)get();
	}
	

	@RequestMapping("/addUser/db/{name}")
	public Object addUserDb(@PathVariable String name)
	{
		//DB deprecated?
		DB db = MongoClientFactory.getMongoClient().getDB("test");
		DBCollection userCollection = db.getCollection("user");
//		Document document = new Document().append("name", name);
		DBObject dbo = new BasicDBObject().append("name", name);
		userCollection.insert(dbo);
		ObjectId id = (ObjectId) dbo.get("_id");
		return id.toString();
	}
	
	@RequestMapping("/updatename/{originName}/{newName}")
	public ResponseEntity<?> updateName(@PathVariable String originName, @PathVariable String newName)
	{
		MongoDatabase database = DataBaseFactory.getDataBase();
		MongoCollection<Document> userCollection = database.getCollection("user");
		userCollection.findOneAndReplace(new Document("name",originName), new Document("name",
				new Document().append("str", newName).append("edittime",new Date())).append("changed",true));
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping("/update/test")
	public ResponseEntity<?> updateTest()
	{
		MongoDatabase database = DataBaseFactory.getDataBase();
		MongoCollection<Document> userCollection = database.getCollection("user");
//		userCollection.updateMany(new Document("name", "hahaha"), new Document("$set",new Document("str",
//				new Document().append("asdf", "qwer").append("qwer", "asdf"))));
//		userCollection.updateMany(new Document("str.asdf.eeee", "asdf"), new Document("$set",new Document("str.asdf.asdf"
//				,new Document("ffff", new Document().append("uuuuu", "123")))));

		userCollection.updateMany(new Document("str.asdf.eeee", "asdf"), 
				new Document("$unset",new Document("str.asdf.asdf",1)));
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping("/distinct/test")
	public List<Document> distinctTest()
	{
		MongoDatabase database = DataBaseFactory.getDataBase();
		MongoCollection<Document> userCollection = database.getCollection("user");
		DistinctIterable<Document> ia = userCollection.distinct("str.asdf", Document.class);
//		DistinctIterable<Document> ia = userCollection.distinct("", Document.class);
		//return []
		MongoCursor<Document> it = ia.iterator();
		List<Document> re = new ArrayList<Document>();
		
		while(it.hasNext())
		{
			re.add(it.next());
		}
		
		return re;
	}
	
	@RequestMapping("/aggregate/test")
	public List<Document> aggregateTest()
	{
		MongoDatabase database = DataBaseFactory.getDataBase();
		MongoCollection<Document> userCollection = database.getCollection("user");
		List<? extends Bson> pipeline = Arrays.asList(new Document("$sort", new Document("name", -1)));
		AggregateIterable<Document> ia = userCollection.aggregate(pipeline);
		MongoCursor<Document> it = ia.iterator();
		List<Document> re = new ArrayList<Document>();
		while(it.hasNext())
		{
			re.add(it.next());
		}
		return re;
	}
	
	@RequestMapping("/addindex/test")
	public void addIndexTest()
	{
		MongoDatabase database = DataBaseFactory.getDataBase();
		MongoCollection<Document> userCollection = database.getCollection("user");
		userCollection.createIndex(new Document("name",1));
		userCollection.createIndex(new Document("str.sdf",1));
		HashMap m;
	}

	@RequestMapping("/sessiontest")
	public int sessionTest(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		Integer access = (Integer) session.getAttribute("access");
		if(access == null)
		{
			session.setAttribute("access",0);
			return 0;
		}
		else
		{
			access ++;
			session.setAttribute("access", access);
			PrintUtil.print(session);
			Enumeration<String> names = session.getAttributeNames();
			return access;
		}

	}
}

//Bson filter;
//CountOptions options;
//userCollection.count(filter, options);
//CodecRegistry codecRegistry = 
//CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)),
//                               MongoClient.getDefaultCodecRegistry());
