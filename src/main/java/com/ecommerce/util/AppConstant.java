package com.ecommerce.util;

public class AppConstant {
	
	public static final String MESSAGE = "message";
	
//	Default value
	public static final String DEFAULT_PAGE_NUMBER = "0";
	public static final String DEFAULT_PAGE_SIZE = "10"; 
	public static final Integer MAX_PAGE_SIZE = 25;
	public static final String DEFAULT_SORT_DIR = "DESC";
	
//	Map key and unauthorizes
	public static final String RESPONSE_MESSAGE="response";
	public static final String UNAUTHORIZED="You don't have permission to make this operation";
	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String INVALID_REQUEST = "Invalid request";
	
	
//	User registration and login
	public static final String RESGISTRATION_SUCCESSFULLY = "User resgistration successfully";
	public static final String INVALID_PHONE_NUMBER = "Invalid mobile number";
	public static final String NUMBER_ALREADY_TAKEN = "This mobile number is already taken";
	public static final String EMAIL_ALREADY_TAKEN = "This Email id is already taken";
	public static final String OTP_GENERATED = "OTP Generated Successfully";
	public static final String INVALID_OTP = "OTP is invalid";
	public static final String OTP_EXPERED = "OTP  Expired";
	public static final String NEW_USER = "New User";
	public static final String USER_DEACTIVE = "User is deactivate";
	public static final String USER_BLOCK = "User is Blocked by admin";
	public static final String ACCOUNT_DEACTIVATE = "User account deactivate successfully";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String UPDATE_SUCCESSFULLY = "User Update Successfully";
	public static final String UPDATE_FAILED = "User Update Failed";
//	Role
	public static final String ROLE_ALREADY_SAVE = "This role is already save";
	
	
//	Product Constant
	public static final String PRODUCT_NOT_FOUND = "Product is not found";
	public static final String PRODUCT_ADDED = "Product added succesfully";
	public static final String PRODUCT_DEACTIVE = "This product is deactive to display";
	public static final String LISTING_STATUS_UPDATE = "Product Listing Status  ";
	public static final String PRODUCT_NOT_VERIFIED = "Product is not verified please wait to get verification from admin";
	public static final String PRODUCT_NAME_TAKEN = "This product name is already taken";

	
//	Address Constant
	public static final String ADDRESS_ADDED = "Address added succesfully";
	public static final String ADDRESS_NOT_FOUND = "This product name is already taken";

//	Category and Sub Category constant
	public static final String CATEGORY_ADDED = "Category successfully added";
	public static final String CATEGORY_TAKEN = "Category alrady used please use other name";
	public static final String DELETE_SUBCATEGORY = "First Delete all its sub category";
	public static final String CATEGORY_NOT_FOUND = "Category Not found";
	public static final String CATEGORY_DELETED = "Category Deleted Successfully";
	public static final String CATEGORY_UPDATED = "Category Update SuccessFully";
	public static final String DELETE_PRODUCT = "First delete all product under this category";
	public static final String SUBCATEGORY_TAKEN = "Subcategory already taken";
	public static final String SUBCATEGORY_UPDATED = "SubCategory updated";
	public static final String SUBCATEGORY_ADDED = "SubCAtegory added successfully";
	public static final String SUBCATEGORY_DELETED = "SubCategory Deleted Successfully";
	public static final String SUB_CATEGORY_NOT_FOUND = "SubCategory Not found";
	
//	varient category and varient category attribute constant
	public static final String VARIENTCAT_TAKEN = "Varient name already taken";
	public static final String VARIENTCAT_ADDED="Varient Added Successfully";
	public static final String DELETE_ALL_ATTRIBUTE = "First delete all its child attribute";
	public static final String VARIENTCAT_DELETED = "Varient Successfully deleted";
	public static final String VARIENT_CATEGORY_NOT_FOUND = "Varient category not found";
	public static final String VARIENTCAT_UPDATED = "Varient category updated successfully";
	public static final String VARIENT_ATTRIBUTE_TAKEN = "Varient attribute taken";
	public static final String DELETE_ALL_PRODUCT = "Delete all product related to this category";
	public static final String ATTRIBUTE_DELETED = "Varient attribute deleted successfully";
	public static final String VARIENT_ATTRIBUTE_ADD = "Varient attribute added";
	public static final String VARIENT_ATTIBUTE_NOT_FOUND = "Attribute is not found";
	public static final String VARIENT_ATTRIBUTE_UPDATE = "Attribute updated";
	

//	Bank Account constant

	public static final String ACCOUNT_NUMBER_TAKEN ="Account number is already in use";
	public static final String ACCOUNT_NOT_FOUND = "Account not found";
	public static final String ERROR_UPDATING_ACCOUNT_STATUS = "Error updating account status";
	public static final String ACCOUNT_DEACTIVATED = "Account Deactivated";
	public static final String ACCOUNT_ACTIVATED = "Account Activated";
	public static final String ACCOUNT_FOUND = "ACCOUNT_FOUND";
	public static final Object ACCOUNTS_FOUND = "Accounts found";
	public static final Object NO_ACCOUNTS_FOUND = "No accounts found";
	public static final Object ACCOUNT_DETAILS_UPDATED = "Account details updated";
	public static final String NO_UPDATING_ACCOUNT_DETAILS = "No update account details";
	public static final String DUPLICATE_ACCOUNT_NUMBER = "duplicate account number ";
	public static final Object UPDATE_ACCOUNT = "Account update ";

//	Image path
	public static final String PRODUCT_IMAGE_PATH = "productImage";
	
	
//	Notification constant
	public static final String NOTIFICATION_TITLE_TAKEN = "Title is already use";
	public static final String NOTIFICATION_ADDED = "Notification send";
	public static final String NOTIFICATION_DELETED = "Notification deleted successfully";
	public static final String NOTIFICATION_UPDATE = "Notification updated";

	
//  Product varient constant
	public static final String NO_ACTIVE_VARIENT = "No active varient present";
	public static final String VARIENT_TAKEN = "Varient Name is taken ";
	public static final String VARIENT_ADDED = "Varient added successfully";
	public static final String VARIENT_NOT_FOUND = "Varient not found";
	
	
// Vendor constant
	public static final String VENDOR_NOT_FOUND = "Vendor is not found";

	
// Entity Constant
	public static final String MOBILE = "Mobile";
	public static final String USER = "User";
	public static final String ID = "Id";
	public static final String NOTIFICATION = "Notification";
	public static final String VARIENT = "Varient";
	public static final String VARIENTCATEGORY = "Varient category";
	public static final String PRODUCT = "Product";
	public static final String VARIENTCATEGORYATTRIBUTE = "Varient category attribute";
	public static final String CATEGORY = "Product category";

	public static final Object REMOVE_FROM_WISHLIST = "remove from wishlist";
	public static final String WISHLIST = "WishList";
	public static final String BRAND_NAME_TAKEN = "Brand Name is taken ";
	public static final String BRAND_IMAGE_PATH = "brandImage";
	public static final Object BRAND_ADD_SUCCES = "Add Brand";
	public static final String STATUS = "status verified";

	
	
	
	public static final String OUT_OF_STOCK = "This product out of stock";
	public static final String AVAILABLE_STOCK = "Product is out of avalibility";
	public static final Object PRODUCT_ADD_TO_CART = "This product is added to card ";
	public static final Object PRODUCT_REMOVE = "Product successfully remove";
	
	
	
	public static final String STATUS_UPDATE = "Status is sucessfully update";
	public static final Object ADDWISHLIST = "Product add to wishlist";
	public static final Object PRODUCT_ALREADY_IN_WISHLIST = "Product already add wishlist";
	public static final Object REMOVE_FROM_WISHLIST_ = "remove from wishlist";
	public static final String PRODUCT_NOT_ADD_WISHLIST = "product not add wishlist";
	public static final String USER_ID_REQUIRED = "user Id Active";
	public static final String ACTIVE_VARIENTS_IN_WISHLIST_NOT_FOUND = "Active varient in wishlist not found";
	public static final Object ACTIVE_VARIENT_IN_WISHLIST_FOUND = "active varient in wishlist found";
	public static final String WISHLIST_NOT_FOUND = "wishlist not fond";

	public static final String PRODUCTSAVEFORLATER_NOT_FOUND = "SaveForLater Not Found";
	public static final String DELETE_SUCCESS = "Delete Succesfully";

	public static final String MESSAGE1 = "Message";

	//public static final Object PRODUCT_SAVE_FOR_LATER = "";

	
	



	public static final  String  PRODUCT_ADDED_SUCCESS= "Added Successfully";
	public static final String PRODUCTREVIEW_NOT_FOUND = "Product REview NOt Found";
	public static final Object UPDATE_PRODUCT_REVIEW = "ProductReview Update Successfully";
	public static final Object PLESE_ORDER_PRODUCT = "Order this Product";
	public static final Object UPDATE_STATUS = "update status";
	public static final Object BRAND_LIST = "list of brand";
	public static final Object VERFIED_BRAND_LIST = "list of verfied brand ";
	public static final Object BRAND_VERIFIED = "brand verfiend";
	public static final Object BRAND_NOT_VERIFIED = "brand  not verfiend";
	public static final String CART = "Cart";

	public static final String CART_ITEM_REMOVE = "Product is succesfully remove from cart";

	public static final String ADD_TO_CART = "Product added to cart";

	public static final String Count = "count";

	public static final String ALREADY_ADDED = "Product is already added";

	public static final String VARIENT_INACTIVE = "Varient is Inactive";

	public static final String INVALID_TRANSITION = "Invalid status transition";

	public static final String IS_PRESENT = "isPresent";

	public static final Object BRAND_UnVERIFIED = " brand Unverified the code ";

	public static final String Identity_IMAGE_PATH = "identityImage";

	public static final Object IDENTITY_ADD_SUCCES = " Add identity ";

	public static final String IDENTITY_NOT_ADD_SUCCES = "identity not add ";

	public static final Object WISHLIST_RETRIVED_SUCCESSFULLY = "whishlist_retrieved_successfully";

	public static final Object ALL_VERFIED_BRAND = "verfied brand";

	public static final Object ALL_ACTIVE_IDENTITY = "identity active successfully" ;

	public static final String COMPLAINT_IMAGE_PATH = "complaintImage" ;

	public static final Object COMPLAINT_ADD_SUCCES = "add complaint" ;

	public static final Object UPDATE_COMPLAINT = "complaint update ";

	public static final String COMPLAINT_NOT_FOUND = "complaint not found";

	
}		

