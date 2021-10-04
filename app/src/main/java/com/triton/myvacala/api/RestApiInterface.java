package com.triton.myvacala.api;

import com.triton.myvacala.requestpojo.ActivityCreateRequest;
import com.triton.myvacala.requestpojo.AddLocationRequest;
import com.triton.myvacala.requestpojo.AddVehicleRequest;
import com.triton.myvacala.requestpojo.AddingCartRequest;
import com.triton.myvacala.requestpojo.AdditionHrsRequest;
import com.triton.myvacala.requestpojo.BookingCreateRequest;
import com.triton.myvacala.requestpojo.BookingHistoryRequest;
import com.triton.myvacala.requestpojo.CheckTimes11Request;
import com.triton.myvacala.requestpojo.CheckTimesRequest;
import com.triton.myvacala.requestpojo.CouponsCodeValidationRequest;
import com.triton.myvacala.requestpojo.FinalpayableUpdateRequest;
import com.triton.myvacala.requestpojo.FinalpayableUpdateResponse;
import com.triton.myvacala.requestpojo.GetCardDetailsRequest;
import com.triton.myvacala.requestpojo.GetCustomerVehicleandLocationRequest;
import com.triton.myvacala.requestpojo.GetPayuRequest;
import com.triton.myvacala.requestpojo.GetUserStatusRequest;
import com.triton.myvacala.requestpojo.LocationDeleteRequest;
import com.triton.myvacala.requestpojo.LocationEditRequest;
import com.triton.myvacala.requestpojo.LocationListRequest;
import com.triton.myvacala.requestpojo.LocationStatusChangeRequest;
import com.triton.myvacala.requestpojo.MainServiceGetListRequest;
import com.triton.myvacala.requestpojo.NotificationListRequest;
import com.triton.myvacala.requestpojo.ParkingBookingCreateRequest;
import com.triton.myvacala.requestpojo.ParkingBookingGetListRequest;
import com.triton.myvacala.requestpojo.ParkingChangeTimesRequest;
import com.triton.myvacala.requestpojo.ParkingExtChangeTimesRequest;
import com.triton.myvacala.requestpojo.ParkingExtendDateTimeRequest;
import com.triton.myvacala.requestpojo.ParkingSlotCheckAvailableRequest;
import com.triton.myvacala.requestpojo.QrcodeCheckinEntryRequest;
import com.triton.myvacala.requestpojo.UserDetailsRequest;
import com.triton.myvacala.responsepojo.AdditionHrsResponse;
import com.triton.myvacala.responsepojo.CheckTimesResponse;
import com.triton.myvacala.responsepojo.GetPayuResponse;
import com.triton.myvacala.responsepojo.ParkingBookingGetListResponse;
import com.triton.myvacala.requestpojo.ParkingListRequest;
import com.triton.myvacala.requestpojo.PopularVehicleListRequest;
import com.triton.myvacala.requestpojo.ProfileUpdateRequest;
import com.triton.myvacala.requestpojo.RemovingCartRequest;
import com.triton.myvacala.requestpojo.SubServiceListRequest;
import com.triton.myvacala.requestpojo.VehicleDeleteRequest;
import com.triton.myvacala.requestpojo.VehicleDetailsRequest;
import com.triton.myvacala.requestpojo.VehicleEditRequest;
import com.triton.myvacala.requestpojo.VehicleListsRequest;
import com.triton.myvacala.requestpojo.VehicleStatusChangeRequest;
import com.triton.myvacala.responsepojo.AddVehicleResponse;
import com.triton.myvacala.responsepojo.AddingCartResponse;
import com.triton.myvacala.responsepojo.BookingCreateResponse;
import com.triton.myvacala.responsepojo.BookingHistoryResponse;
import com.triton.myvacala.responsepojo.CouponsCodeValidationResponse;
import com.triton.myvacala.responsepojo.FAQResponse;
import com.triton.myvacala.responsepojo.GetCardDetailsResponse;
import com.triton.myvacala.responsepojo.GetCustomerVehicleandLocationResponse;
import com.triton.myvacala.responsepojo.GetUserStatusResponse;
import com.triton.myvacala.responsepojo.LocationStatusChangeResponse;
import com.triton.myvacala.responsepojo.MainServiceGetListResponse;
import com.triton.myvacala.responsepojo.NotificationListResponse;
import com.triton.myvacala.responsepojo.ParkingBookingCreateResponse;
import com.triton.myvacala.responsepojo.ParkingChangeTimesResponse;
import com.triton.myvacala.responsepojo.ParkingExtChangeTimesResponse;
import com.triton.myvacala.responsepojo.ParkingExtendDateTimeResponse;
import com.triton.myvacala.responsepojo.ParkingListResponse;
import com.triton.myvacala.responsepojo.ParkingLocationGetListResponse;
import com.triton.myvacala.responsepojo.ParkingSlotCheckAvailableResponse;
import com.triton.myvacala.responsepojo.PopularVehicleListResponse;
import com.triton.myvacala.requestpojo.VehicleBrandListRequest;
import com.triton.myvacala.requestpojo.MasterServiceGetlistRequest;
import com.triton.myvacala.requestpojo.NewUserRequest;
import com.triton.myvacala.requestpojo.RegisterMobileRequest;
import com.triton.myvacala.responsepojo.QrcodeCheckinEntryResponse;
import com.triton.myvacala.responsepojo.RemovingCartResponse;
import com.triton.myvacala.responsepojo.SplashScreenListResponse;
import com.triton.myvacala.responsepojo.SubServiceListResponse;
import com.triton.myvacala.responsepojo.UserDetailsResponse;
import com.triton.myvacala.responsepojo.VehicleDeleteResponse;
import com.triton.myvacala.responsepojo.VehicleEditResponse;
import com.triton.myvacala.responsepojo.VehicleListsResponse;
import com.triton.myvacala.responsepojo.ActivityCreateResponse;
import com.triton.myvacala.responsepojo.AddLocationResponse;
import com.triton.myvacala.responsepojo.GetServiceListResponse;
import com.triton.myvacala.responsepojo.LocationDeleteResponse;
import com.triton.myvacala.responsepojo.LocationEditResponse;
import com.triton.myvacala.responsepojo.LocationListResponse;
import com.triton.myvacala.responsepojo.VehicleBrandListResponse;
import com.triton.myvacala.responsepojo.MasterServiceGetlistResponse;
import com.triton.myvacala.responsepojo.NewUserResponse;
import com.triton.myvacala.responsepojo.RegisterMobileResponse;
import com.triton.myvacala.responsepojo.VehicleDetailsResponse;
import com.triton.myvacala.responsepojo.VehicleStatusChangeResponse;
import com.triton.myvacala.responsepojo.VehicleTypeGetListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RestApiInterface {


    /*mobile splash screen list*/
    @GET("mobilesplashscreen/mobile/getlist")
    Call<SplashScreenListResponse> splashScreenListResponseCall(@Header("Content-Type") String type);

    /*Get user status*/
    @POST("mobile/getuserstatus")
    Call<GetUserStatusResponse>getUserStatusResponseCall(@Header("Content-Type") String type, @Body GetUserStatusRequest getUserStatusRequest);

    /*Login*/
    @POST("mobile/registermobile")
    Call<RegisterMobileResponse>registerMobileResponseCall(@Header("Content-Type") String type, @Body RegisterMobileRequest registerMobileRequest);

    /*Activity monitor*/
    @POST("activity/create")
    Call<ActivityCreateResponse>activityCreateResponseCall(@Header("Content-Type") String type, @Body ActivityCreateRequest activityCreateRequest);

    /*Register*/
    @POST("mobile/newuser")
    Call<NewUserResponse>newUserResponseCall(@Header("Content-Type") String type, @Body NewUserRequest newUserRequest);


    /*Profile update*/
    @POST("edit")
    Call<NewUserResponse>profileUpdateResponseCall(@Header("Content-Type") String type, @Body ProfileUpdateRequest profileUpdateRequest);


    /*Service available location list*/
    @GET("location/mobile/getlist")
    Call<GetServiceListResponse> getServiceListResponseCall(@Header("Content-Type") String type);

    /*Add location*/
    @POST("mobile/addlocation")
    Call<AddLocationResponse> addLocationResponseCall(@Header("Content-Type") String type, @Body AddLocationRequest addLocationRequest);

    /*Location lists*/
    @POST("customer/mobile/locationlist")
    Call<LocationListResponse> locationListResponseCall(@Header("Content-Type") String type, @Body LocationListRequest locationListRequest);

    /*location list edit*/
    @PUT("customer/mobile/locationedit")
    Call<LocationEditResponse> locationEditResponseCall(@Header("Content-Type") String type, @Body LocationEditRequest locationEditRequest);

    /*location list delete*/
    @HTTP(method = "DELETE", path = "customer/mobile/delete", hasBody = true)
    Call<LocationDeleteResponse> locationDeleteResponseCall(@Header("Content-Type") String type, @Body LocationDeleteRequest locationDeleteRequest);

    /*Location status change*/
    @PUT("customer/mobile/statuschange")
    Call<LocationStatusChangeResponse>locationStatusChangeResponseCall(@Header("Content-Type") String type, @Body LocationStatusChangeRequest locationStatusChangeRequest);

    /*vehicle type list*/
    @GET("vehicletype/mobile/getlist")
    Call<VehicleTypeGetListResponse> vehicleTypeGetListResponseCall(@Header("Content-Type") String type);

    /*popular vehicle list*/
    @POST("vehiclebrand/mobile/popular/getlist")
    Call<PopularVehicleListResponse> popularVehicleListResponseCall(@Header("Content-Type") String type, @Body PopularVehicleListRequest popularVehicleListRequest);

    /*brand vehicle list*/
    @POST("vehiclebrand/mobile/brandlist")
    Call<VehicleBrandListResponse> vehicleBrandListResponseCall(@Header("Content-Type") String type, @Body VehicleBrandListRequest brandListRequest);

    /*Fueltype and model type fetch vehicle details*/
    @POST("vehiclename/mobile/fetchvehicledetails")
    Call<VehicleDetailsResponse> vehicleDetailsResponseCall(@Header("Content-Type") String type, @Body VehicleDetailsRequest vehicleDetailsRequest);

    /*Add vehicle*/
    @POST("vehicle/mobile/create")
    Call<AddVehicleResponse> addVehicleResponseCall(@Header("Content-Type") String type, @Body AddVehicleRequest addVehicleRequest);

    /*List of the added vehicles*/
    @POST("vehicle/mobile/cvehiclelist")
    Call<VehicleListsResponse> vehicleListsResponseCall(@Header("Content-Type") String type, @Body VehicleListsRequest vehicleListsRequest);

    /*Vehicle status change*/
    @PUT("vehicle/mobile/statuschange")
    Call<VehicleStatusChangeResponse>vehicleStatusChangeResponseCall(@Header("Content-Type") String type, @Body VehicleStatusChangeRequest vehicleStatusChangeRequest);

    /*vehicle list delete*/
    @POST("vehicle/mobile/delete")
    Call<VehicleDeleteResponse> vehicleDeleteResponseCall(@Header("Content-Type") String type, @Body VehicleDeleteRequest vehicleDeleteRequest);

    /*Vehicle list edit*/
    @PUT("vehicle/mobile/edit")
    Call<VehicleEditResponse>vehicleEditResponseCall(@Header("Content-Type") String type, @Body VehicleEditRequest vehicleEditRequest);


    /*Dashboard with master services*/
    @POST("mobile/Dashboard")
    Call<MasterServiceGetlistResponse> masterServiceGetlistResponseCall(@Header("Content-Type") String type, @Body MasterServiceGetlistRequest masterServiceGetlistRequest);

    /*Main service list*/
    @POST("service/mobile/mainservicegetlist")
    Call<MainServiceGetListResponse> mainServiceGetListResponseCall(@Header("Content-Type") String type, @Body MainServiceGetListRequest mainServiceGetListRequest);

    /*Sub service list*/
    @POST("subservice/mobile/subservicelist")
    Call<SubServiceListResponse> subServiceListResponseCall(@Header("Content-Type") String type, @Body SubServiceListRequest subServiceListRequest);

    /*Adding Cart*/
    @POST("cart/create")
    Call<AddingCartResponse> addingCartResponseCall(@Header("Content-Type") String type, @Body AddingCartRequest addingCartRequest);

    /*Removing Cart*/
    @POST("cart/Cartremoved")
    Call<RemovingCartResponse> removingCartResponseCall(@Header("Content-Type") String type, @Body RemovingCartRequest removingCartRequest);

    /*Get Cart Details*/
    @POST("cart/customer/getcartdetails")
    Call<GetCardDetailsResponse> getCardDetailsResponseCall(@Header("Content-Type") String type, @Body GetCardDetailsRequest getCardDetailsRequest);


    /*Booking create*/
    @POST("booking/create")
    Call<BookingCreateResponse> bookingCreateResponseCall(@Header("Content-Type") String type, @Body BookingCreateRequest bookingCreateRequest);



    /*Booking History/Order History*/
    @POST("booking/bookinghistory")
    Call<BookingHistoryResponse> bookingHistoryResponseCall(@Header("Content-Type") String type, @Body BookingHistoryRequest bookingHistoryRequest);

    /*Notification List*/
    @POST("notification/mobile/getlist")
    Call<NotificationListResponse> notificationListResponseCall(@Header("Content-Type") String type, @Body NotificationListRequest notificationListRequest);


    /*FAQ List*/
    @GET("faq/getlist")
    Call<FAQResponse> fAQResponseCall(@Header("Content-Type") String type);


    /*Coupons validation check*/
    @POST("coupons/codevalidation")
    Call<CouponsCodeValidationResponse> couponsCodeValidationResponseCall(@Header("Content-Type") String type, @Body CouponsCodeValidationRequest couponsCodeValidationRequest);


    /*order history final payable update*/
    @POST("booking/finalpayable_update")
    Call<FinalpayableUpdateResponse> finalpayableUpdateResponseCall(@Header("Content-Type") String type, @Body FinalpayableUpdateRequest finalpayableUpdateRequest);


    /*Parking Module*/
    /*Parking Dashboard */
    @POST("parking/parkingareadetails/getprarking_list")
    Call<ParkingListResponse> parkingListResponseCall(@Header("Content-Type") String type, @Body ParkingListRequest parkingListRequest);

    /*Get Customer Vehicle and Location Data*/
    @POST("service/get_user_default")
    Call<GetCustomerVehicleandLocationResponse> getCustomerVehicleandLocationResponseCall(@Header("Content-Type") String type, @Body GetCustomerVehicleandLocationRequest getCustomerVehicleandLocationRequest);

    /*parking booking create*/
    @POST("parking/parkingbooking/create")
    Call<ParkingBookingCreateResponse> parkingBookingCreateResponseCall(@Header("Content-Type") String type, @Body ParkingBookingCreateRequest parkingBookingCreateRequest);

    /* booking history of parking */
    @POST("parking/parkingbooking/getlist_id")
    Call<ParkingBookingGetListResponse> parkingBookingGetListResponseCall(@Header("Content-Type") String type, @Body ParkingBookingGetListRequest parkingBookingGetListRequest);

    /*Parking details page slot check available*/
    @POST("parking/parkingareadetails/parking_slot_check_available")
    Call<ParkingSlotCheckAvailableResponse> parkingSlotCheckAvailableResponseCall(@Header("Content-Type") String type, @Body ParkingSlotCheckAvailableRequest parkingSlotCheckAvailableRequest );


    /*Parking details change date and time*/
    @POST("parking/parkingareadetails/parking_change_times")
    Call<ParkingChangeTimesResponse> parkingChangeTimesResponseCall(@Header("Content-Type") String type, @Body ParkingChangeTimesRequest parkingChangeTimesRequest);


    /*Parking details page time extensions slot check available*/
    @POST("parking/parkingareadetails/parking_slot_extance_check_available")
    Call<ParkingExtendDateTimeResponse> parkingExtendDateTimeResponseCall(@Header("Content-Type") String type, @Body ParkingExtendDateTimeRequest parkingExtendDateTimeRequest);

    /*Extend parking get amount*/
    @POST("parking/parkingareadetails/parking_ext_change_times")
    Call<ParkingExtChangeTimesResponse> parkingExtChangeTimesResponseCall(@Header("Content-Type") String type, @Body ParkingExtChangeTimesRequest parkingExtChangeTimesRequest);


    /*Parking qrcode checkin entry*/
    @POST("parking/parkingbooking/checking_entry")
    Call<QrcodeCheckinEntryResponse> qrcodeCheckinEntryResponseCall(@Header("Content-Type") String type, @Body QrcodeCheckinEntryRequest qrcodeCheckinEntryRequest);


    /*Parking qrcode checkout entry*/
    @POST("parking/parkingbooking/checking_exit")
    Call<QrcodeCheckinEntryResponse> qrcodeCheckoutEntryResponseCall(@Header("Content-Type") String type, @Body QrcodeCheckinEntryRequest qrcodeCheckinEntryRequest);

     /*Parking qrcode checkout entry*/
    @POST("parking/parkingbooking/additional_hrs_crs")
    Call<AdditionHrsResponse> additionHrsResponseCall(@Header("Content-Type") String type, @Body AdditionHrsRequest additionHrsRequest);


    /*Parking check times*/
    @POST("parking/parkingareadetails/checktimes")
    Call<CheckTimesResponse> checkTimesResponseCall(@Header("Content-Type") String type, @Body CheckTimesRequest checkTimesRequest);

    /*Parking check times*/
    @POST("parking/parkingareadetails/counter_checktime")
    Call<CheckTimesResponse> counterChecktimeResponseCall(@Header("Content-Type") String type, @Body CheckTimesRequest checkTimesRequest);


    @POST("parking/parkingareadetails/checktimes2")
    Call<CheckTimesResponse> checkTimes2ResponseCall(@Header("Content-Type") String type, @Body CheckTimes11Request checkTimes11Request);


    /*Parking available location list*/
    @GET("parkinglocation/admin/getlist")
    Call<ParkingLocationGetListResponse> parkingLocationGetListResponseCall(@Header("Content-Type") String type);



    /*Get user details*/
    @POST("mobile/map/userdetails")
    Call<UserDetailsResponse> userDetailsResponseCall(@Header("Content-Type") String type, @Body UserDetailsRequest userDetailsRequest);




    /*Parking all delete history*/
    /*http://15.207.51.203:3000/parking/parkingbooking/deletes*/







}
