package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
	
	
	

		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();

	}
	
	@POST
	@Path("/ConfirmService")
	public void AcceptService(int uid1, int uid2) {
		boolean accept = false;
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("friends");
		PreparedQuery pq = ds.prepare(q);
		List<Entity> friends = pq.asList(FetchOptions.Builder.withDefaults());
		
		for (Entity entity : pq.asIterable()) {
			if (Boolean.parseBoolean(entity.getProperty("accept").toString()) == true) {
				return;
			}
		}
		accept = true;
		Entity friend = new Entity("friends", friends.size() + 1);
		friend.setProperty("uid1", uid1);
		friend.setProperty("uid2", uid2);
		friend.setProperty("accept", accept);
		ds.put(friend);
		
	}
	
	/**
	 * Send Friend Request Rest Service, this service will be called to make Friend Request process
	 * @param uid1 provided sender user id
	 * @param uid2 provided reciever user id
	 * @return user in json format
	 */
	@POST
	@Path("/SendFriendRequest")
	public String sendFriendRequest(@FormParam("uid1") String uid1,
			@FormParam("uid2") String uid2) {
		JSONObject object = new JSONObject();
		Boolean user = UserEntity.sendRequest(uid1, uid2);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
		}

		return object.toString();

	}
}