package com.triton.myvacala;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sugan on 15/3/17.
 */
public interface Constants {

    interface AppKey {
        String EXTERNAL_FOLDER_NAME = "abctaxi/driver";
        String GOOGLE_API_KEY = "AIzaSyB8Ca7FwyJW6wApeBIwaN7g6gq-MZOAZHM";
        String MODE = "ANDROID";
        String APP_TOKEN = "DAPP";
        LatLng CHENNAI_LATLNG = new LatLng(13.067439, 80.237617);
        String FILE_PROVIDER_AUTHORITY = "com.abctaxi.driver.fileprovider";
        String LOG_FILE_NAME = "abctaxiLogs.txt";
        String WAITING_REQUEST = "waiting_request";
        String FORCE_REQUEST = "force_request";
        String FORCE_UPDATE = "FORCE_UPDATE";
        String SIGNOUT_EXPIRY = "signout_expiry";
        String FORCE_TRIP_STATUS = "force_trip_status";
        int OFF_DUTY = 0;
        int ON_DUTY = 1;

        int GO_HOME = 2;
        String SOCKET_CONNECTION_CHECK = "SOCKET_CONNECTION_CHECK";
        String WAKE_APP = "WAKE_APP";

    }

    interface Receiver {
        String RECEIVER_KEY = "com.abctaxi.driver.request.receiver";
        String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
        String LOCATION_RECEIVER = "com.abctaxi.driver.location.receiver";
        String FCM_TOKEN_FILTER_ACTION = "com.abctaxi.driver.fcmtocken";
    }

    interface UserType {
        String DRIVER = "driver";
        String PASSENGER = "passenger";
    }

    interface TripStatus {
        int CANCELLED = 0;
        int ABORTED = 1;
        int ASSIGNED = 3;
        int REACHED = 4;
        int BOARDED = 5;
        int IN_PROGRESS = 6;
        int STOPPED = 7;
        int PAID = 8;
        int COMPLETED = 9;
    }

    interface Headers {
        String AUTHORIZATION = "x-access-token";
        String APP_AUTHORIZATION = "x-app-token";
        String USER_AUTHORIZATION = "x-user-token";
    }

    interface Message {
        String NAME_EMPTY = "Name should not be empty";
        String NAME_VALIDATION_FAILED = "Name should be more than 2 characters";
        String EMAIL_EMPTY = "Email should not be empty";
        String EMAIL_VALIDATION_FAILED = "Please enter a valid email address";
        String NO_INTERNET = "No Internet Connection";
        String VERIFICATION_CODE_EMPTY = "Verification code should not be empty";
        String VERIFICATION_CODE_VALIDATION_FAILED = "Verification code should be more than 3 characters";
        String UNKNOWN = "UnKnown";
    }

    interface AlertMessage {
        //common messages for api failure
        String NO_INTERNET_CONNECTION = "Please check your internet connection and try again.";
        String DATA_NOT_FOUND = "No data found. Contact our support center.";
        String JSON_PARSING = "Unable to send data to our server. Contact our support center.";
        String TIME_OUT = "Unable to connect to our server. Make sure you are having a stable internet connection.";
        String SERVER_CONNECTION_FAILED = "Unable to connect to our server. Make sure you are having a stable internet connection.";
        String UNABLE_TO_CONNECT_SERVER = "Unable to connect to our server. Please try again later.";
        String LOG_OUT_TITLE = "Log Out?";
        String LOG_OUT_CONTENT = "Do you want to Continue?";
        String LOG_IN_EXPIRED_TITLE = "Login Expired!";
        String LOG_IN_EXPIRED_CONTENT = "Oops! User logged in other device.";
        String YES = "YES";
        String CANCEL = "CANCEL";
        String LocationIssue = "Coudn't fetch your current location, Kindly try again";
    }

    interface BundleKey {
        String CURRENT_POSITION = "current_position";
        String CURRENT_VALUE = "current_value";
        String REQUEST = "request";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String LOGIN_REQUEST_OBJECT = "login_request";
        String CONTACT_LIST = "contactList";
        String PHONE_NUMBER = "phoneNumber";
        String ORDER = "order";
        String IMAGE_HANDLER_IMAGE_TYPE = "imageHandlerImageType";
        String IMAGE_HANDLER_IMAGE_LOCATION = "imageHandlerImageLocation";
        String TRIP_ID = "trip_id";
        String INVOICE_KEY = "invoice_key";
        String FCM_TOKEN = "FCM_TOKEN";
        String TYPE = "Type";
        String IS_FROM_REGISTRATION = "isFromRegistration";
        String DOCUMENTS = "documents";
    }

    interface SharedKey {
        String SERVER_TIME = "serverTime";
        String APP_KEY = "app_key";
        String DISPLAY_NAME = "display_name";
        String DISPLAY_PIC = "display_pic";
        String AUTH_TOKEN = "auth_token";
        String USER_TOKEN = "user_token";
        String DRIVER_ID = "driver_id";
        String FORCED_PWD_CHANGE = "forced_pwd_change";
        String DEFAULT_TYPE = "default_type";
        String CURRENT_TYPE = "current_type";
        String CURRENT_TYPE_NAME = "current_type_name";
        String DRIVER_PHONE = "driver_phone";
        String OTP = "otp";
        String PROFILE_CREATED = "profile_created";
        String TOTAL_RIDES = "total_rides";
        String BALANCE = "balance";
        String MILEAGE = "mileage";
        String ONLINE_DURATION = "online_duration";
        String REVENUE = "revenue";
        String DUTY_STATUS = "duty_status";
        String VEHICLE_DETAILS = "vehicle_details";
        String ACTIVE_BOOKING_ID = "active_booking_id";
        String TRIP_DETAILS = "trip_details";
        String LAST_KNOWN_LOCATION = "last_known_location";
        String LAST_KNOWN_BULK_LOCATION = "last_known__Bulk_location";
        String FCM_TOCKEN = "FCM_TOCKEN";
        String IS_LOGGEDIN = "IS_LOGGEDIN";
        String IS_FORGROUND = "IS_FORGROUND";
        String CURRENT_LOCATION = "current_location";

        String TNC = "TNC";

    }

    interface ErrorCode {
        //custom error codes for internal data handling
        int ERROR = 1443;
        int DATA_NOT_FOUND = 1444;
        int JSON_PARSING = 1445;
        int SERVICE_UNAVAILABLE = 1447;
        int SERVER_CONNECTION_FAILED = 1448;
        int TIME_OUT = 1449;

        //defined error codes from api
        int SESSION_EXPIRED = 401;
    }

    interface BroadcastKey {

        String CANCELLATION_RIDE = "com.oneway.drivers.ORDER_CANCELLATION";
        String CANCELLATION_REQ = "com.oneway.drivers.ORDER_REQ_CANCELLATION";
    }

    interface ServerKey {
        String AUTHORIZATION = "authorization";
        String SOURCE_TYPE = "sourceType";
        String PROFILE_PIC = "PROFILE";
    }

    enum ValidationKey {
        EMAIL,
        NAME
    }

    interface PermissionMessages {
        String STORAGE = "Enable storage permission to continue";
        String CAMERA_DENIED = "Enable storage permission to continue camera";
        String STORAGE_DENIED = "Enable storage permission to continue";
        String FOREGROUND_DENIED = "Enable foreground service permission to continue";
        String PHONE_STATE = "Enable phone state permission to continue";
    }

    interface Title {
        String Registration = "Register";
        String CHANGE_PASSWORD = "Change Password";
        String RESET_PASSWORD = "Reset Password";
        String VERIFICATION = "Verification";
        String PROFILE = "Profile";
        String TARIFF = "Tariff";
        String DUTY_LOG = "Duty Log";
        String REVENUE = "Revenue";
        String TERMS = "Terms & Conditions";
        String TOPUP_LOG = "TopUp History";
    }

    interface ScreenType {
        String CHANGE_PASSWORD_SCREEN = "changePassword";
    }

    public interface RequestCodes {
        int TRIP_DETAL = 1001;
        int NAVIGATION_REQUEST_CODE = 1002;
    }

}
