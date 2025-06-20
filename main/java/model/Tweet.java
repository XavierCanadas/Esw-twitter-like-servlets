package model;
import java.sql.Timestamp;

public class Tweet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	 private int id;
	 private int uid;
	 private String username;
	 private String profilePictureUrl;
	 private Timestamp postDateTime;
	 private String content;
	 private Integer likesCount;
	 private Integer parentId;
	 private boolean isLikedByCurrentUser;

	 public Tweet() {
	 }

	 public Integer getId() {
		 return this.id;
	 }
	 
	 public void setId(Integer id) {
		 this.id = id;
	 }

	 public int getUid() {
		 return this.uid;
	 }
	 
	 public void setUid(int uid) {
		 this.uid = uid;
	 }
	 
	 public String getUsername() {
		 return this.username;
	 }
	 
	 public void setUsername(String username) {
		 this.username = username;
	 }
	 
	 public Timestamp getPostDateTime() {
		 return this.postDateTime;
	 }
	 public void setPostDateTime(Timestamp postDateTime) {
		 this.postDateTime = postDateTime;
	 }
	 
	 public String getContent() {
		 return this.content;
	 }
	 public void setContent(String content) {
		 this.content = content;
	 }

	public Integer getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Integer likesCount) {
		this.likesCount = likesCount;
	}

	public boolean isLikedByCurrentUser() {
		return isLikedByCurrentUser;
	}

	public void setLikedByCurrentUser(boolean likedByCurrentUser) {
		isLikedByCurrentUser = likedByCurrentUser;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}