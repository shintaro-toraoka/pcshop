package model;

import java.time.LocalDateTime;

public class Payment {

	private String userId;
	private String userName;
	private String productId;
	private String productName;
	private int amount;
	private int quantity;
	private LocalDateTime purchaseDate;
	private String historyId;

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
		}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId (String productId) {
		 this.productId = productId;
	}
	
	
	public String getUserName() {
		return userName;
		}
	
	public String getProductName() {
		return productName;
		}
	
		public int getAmount() {
		return amount;
		}
		
		public int getQuantity() {
		return quantity;
		}
	
		public LocalDateTime getPurchaseDate() {
		return purchaseDate;
		}
		
		public void setUserName(String userName) {
		this.userName = userName;
		}
		
		public void setProductName(String productName) {
		this.productName = productName;
		}
		
		public void setAmount(int amount) {
		this.amount = amount;
		}
		
		public void setQuantity(int quantity) {
		this.quantity = quantity;
		}
		
		public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
		}

		public String getHistoryId() {
			return historyId;
		}

		public void setHistoryId(String historyId) {
			this.historyId = historyId;
		}
}
