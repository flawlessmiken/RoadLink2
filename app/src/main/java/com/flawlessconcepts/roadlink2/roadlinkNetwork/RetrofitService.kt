package com.flawlessconcepts.roadlink.roadlink

import com.flawlessconcepts.roadlink.roadlinkNetwork.BookingItem
import com.flawlessconcepts.roadlink.roadlinkNetwork.CustomerItem
import com.flawlessconcepts.roadlink.roadlinkNetwork.LocationMatrix
import retrofit2.Call
import retrofit2.http.*


//https://www.road-link.biz/webresources/rest/getCustomerService.
interface RetrofitService {

    @GET("/webresources/rest/getCustomerService")
    fun getCustomer(@Query("phone") phone: String): Call<CustomerItem>

    @GET("/webresources/rest/viewBookingService")
    fun getBooking(@Query("bookingID") bookingID: Long): Call<BookingItem>


    @PUT("/webresources/rest/removeBookingService")
    fun deleteBooking(@Query("bknID") bookingID: Long): Call<BookingItem>


    @GET("/webresources/rest/getDistanceMatrixService")
    fun getDistanceMatrix(
        @Query("fromLocation") fromLocation: String?,
        @Query("toLocation") toLocation: String?
    ): Call<LocationMatrix>

    @GET("/webresources/rest/addBookingService")
    fun addBooking(
        @Query("tripDistance") tripDistance: Double,
        @Query("hasReturn") hasReturn: String?,
        @Query("calculatedCost") calculatedCost: Double,
        @Query("departureTime") departureTime: String?,
        @Query("customerPhone") customerPhone: String?,
        @Query("destination") destination: String?,
        @Query("numberOfPassengers") numberOfPassengers: Int,
        @Query("locationAddress") locationAddress: String?
    ): Call<BookingItem>

    @GET("/webresources/rest/calculatePriceService")
    fun calculateCostService(
        @Query("location") location: String?,
        @Query("destination") destination: String?,
        @Query("hasReturn") hasReturn: String?,
        @Query("departureTime") departureTime: String?,
        @Query("locationAddress") locationAddress: String?,
        @Query("customerPhone") customerPhone: String?,
        @Query("numbOfPassengers") numberOfPassengers: String?

    ): Call<BookingItem>



//    interface TaskService {
//        @POST("/tasks")
//        fun createTask(@Body task: Task?): Call<Task?>?
//    }

   /*@Path("addBookingService")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(
        ClassNotFoundException::class,
        IOException::class,
        Exception::class
    )
    fun addBookingService(
        @QueryParam("tripDistance") tripDistance: Double,
        @QueryParam("hasReturn") hasReturn: String?,
        @QueryParam("calculatedCost") calculatedCost: Double,
        @QueryParam("departureTime") departureTime: String?,
        @QueryParam("customerPhone") customerPhone: String?,
        @QueryParam("destination") destination: String?,
        @QueryParam("numberOfPassengers") numberOfPassengers: Int,
        @QueryParam("locationAddress") locationAddress: String?
    ): Booking? {
        val booking = Booking()
        booking.setTripDistance(tripDistance)
        booking.setHasReturn(hasReturn)
        booking.setCalculatedCost(calculatedCost)
        booking.setDepartureTime(departureTime)
        booking.setCustomerID(customerPhone)
        booking.setDestination(destination)
        booking.setNumbOfPassengers(numberOfPassengers)
        booking.setLocationAddress(locationAddress)
        return BookingDAO().addBooking(booking)
    }

    */





}
