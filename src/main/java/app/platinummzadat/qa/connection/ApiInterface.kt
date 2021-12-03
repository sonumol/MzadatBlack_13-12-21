package app.platinummzadat.qa.connection
import app.platinummzadat.qa.data.models.AmountModel
import okhttp3.MultipartBody
import org.json.JSONObject

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @GET("get_minimum_deposit_amount")
    fun getAmount(): Call<AmountModel>

//    @POST("auth")
//    fun doLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>
//
//    @POST("user")
//    fun doRegister(@Body registerRequest: RegisterRequest): Call<CommonResponse>
//
//    //category
//    @GET("categories/list")
//    fun getCategories(@Header("x-auth-token") token: String): Call<CategoryResponse>
//
//    @GET("categories/list/{type}")
//    fun getCategorieswithType(@Header("x-auth-token") token: String,@Path("type") type:String): Call<CategoryResponse>
//
//    @POST("categories")
//    fun postCategory(@Header("x-auth-token") token: String, @Body categoryRequest: CategoryRequest): Call<CatResponse>
//
//
//    @DELETE("categories/{categoryId}")
//    fun deleteCategory(@Header("x-auth-token") token: String, @Path("categoryId") categoryId:String): Call<PasswordUpdateResponse>
//
//
//    @GET("user/me")
//    fun getuserDetails(@Header("x-auth-token") token: String): Call<UserResponse>
//    //Team calls starts from here
//
//
//    @POST("teams")
//    fun createTeam(
//        @Header("x-auth-token") token: String, @Body body: CreateItemRequest2
//    ): Call<CreateTeamResponse>
//
//
//    @GET("teams")
//    fun  getTeamList(
//        @Header("x-auth-token") token: String
//    ): Call<TeamListResponse>
//
//
//    @GET("teams/{teamId}")
//    fun getTeam(
//        @Header("x-auth-token") token: String, @Path("teamId") teamId: String?
//    ): Call<TeamFullViewRes>
//
//    @POST("requests")
//    fun teamJoinRequests(@Header("x-auth-token") token: String,@Body teamJoinRequest: TeamJoinRequest): Call<ResetPasswordResponse>
//
//    @GET("requests")
//    fun getTeamJoinRequests(@Header("x-auth-token") token: String): Call<TeamRequestList>
//    //get my inivte requests
//    @GET("requests/my-requests")
//    fun getMyreqeusts(@Header("x-auth-token") token: String): Call<TeamInviteListRes>
//
//    //Reimbursement
//    @POST("reimbursements")
//    fun createReimbursement(@Header("x-auth-token") token: String, @Body reimburseRequest: ReimburseRequest): Call<ReimbursementResponse>
//
//    //edit Reimbursement
//    @PUT("reimbursements/{reimbursementId}")
//    fun editReimbursement(@Header("x-auth-token") token: String,@Path("reimbursementId") reimbursementId: String, @Body reimburseRequest: ExpenseEditReq): Call<EditExpenseResponse>
//
//    @POST("reimbursements/list")
//    fun reimbursementList(@Header("x-auth-token") token: String,@Body expenseListReq: ExpenseListReq?): Call<ExpenseListResData>
//    // projects
//
//    @GET("projects")
//    fun getProjectList(@Header("x-auth-token") token: String): Call<ProjecListtResponse>
//
//    @POST("projects")
//    fun createProject(@Header("x-auth-token") token: String, @Body createItemRequest: CreateItemRequest): Call<ProjectResponse>
//
//    @PUT("projects/{projectId}")
//    fun udateProject(@Header("x-auth-token") token: String, @Path("projectId") projectId: String, @Body createItemRequest: CreateItemRequest): Call<ProjectResponse>
//
//    @DELETE("projects/{projectId}")
//    fun deleteroject(@Header("x-auth-token") token: String, @Path("projectId") projectId: String): Call<ProjectResponse>
//
//    @POST("password/generate-token")
//    fun sendResetToken(@Body resetPasswordRequest: ResetPasswordRequest):Call<ResetPasswordResponse>
//
//    @PUT("password/update-password")
//    fun updatePassword(@Body updatePasswordRequest: UpdatePasswordRequest):Call<PasswordUpdateResponse>
//
//    @GET("icon")
//    fun getAllIcons(@Header("x-auth-token") token: String):Call<AllCategoryResponse>
//
//    @Multipart
//    @POST("upload/{reimbursementId}")
//    fun uploadBill(@Header("x-auth-token") token: String,@Path("reimbursementId") reimburseId:String, @Part filePart:MultipartBody.Part ):Call<BillUploadResponse>
//
//    @GET("reimbursements/{reimbursementId}")
//    fun getReimburseDetail(@Header("x-auth-token") token: String, @Path("reimbursementId") id:String): Call<ExpenseDetailsRes>
//
//    @GET("logs/{reimbursementId}")
//    fun getExpenseLogs(@Header("x-auth-token") token: String, @Path("reimbursementId") id:String): Call<ExpenseLogsResponse>
//
//    @PUT("reimbursements/status/{reimbursementId}")
//     fun updateReimburseStatus(@Header("x-auth-token") token: String, @Path("reimbursementId") id:String, @Body updateStatus: UpdateStatus):Call<JSONObject>
//
//    @POST("comments/{reimbursementId}")
//    fun addComment(@Header("x-auth-token") token: String, @Path("reimbursementId") id:String, @Body addCommentRequest: AddCommentRequest):Call<JSONObject>
//
//    @GET("comments/{reimbursementId}")
//    fun getComments(@Header("x-auth-token") token: String, @Path("reimbursementId") id:String):Call<CommentListResponse>
//
//    @PUT("requests/{requestId}")
//    fun updateTeamRequest(@Header("x-auth-token") token: String, @Path("requestId") id:String, @Body teamRequestReply: TeamRequestReply):Call<RequestApproveRes>
//
//    //new apis
//    @POST("otp/generate")
//    fun doSignUp(@Body registerRequest: RegisterRequestN): Call<SignUpRes>
//
//    @POST("user")
//    fun doRegisterNew(@Body registerRequest: UserDetailsUploadReq): Call<RegisterRes>
//
//    @PUT("user/update-currency")
//    fun doUpdateCurrency(@Header("x-auth-token") token: String,@Body body : CurrencyReq): Call<CurrencyUpdateREs>
//
//    @POST("auth")
//    fun doLogin2(@Body loginRequest: LoginRequest): Call<LoginResponse2>
//
//    //google auth
//    @POST("user/google")
//    fun doGoogleReg(@Body registerRequest: GoogleRegisterReq): Call<LoginResponse2>
//
//    //team delete
//    @DELETE("teams/{teamId}")
//    fun deleteteam(@Header("x-auth-token") token: String, @Path("teamId") teamId: String): Call<CommonResponse>
//
//    //team member delete
//    @DELETE("teams/member/{teamId}/{memeberId}/{type}")
//    fun deleteTeamMeber(@Header("x-auth-token") token: String, @Path("teamId") teamId: String,@Path("memeberId") memeberId: String,@Path("type") type: String): Call<CommonResponse>
//
//    //update team name
//    @PUT("teams/{teamId}")
//    fun updateteamname(@Header("x-auth-token") token: String,
//                       @Path("teamId") teamId: String,
//                       @Body body: CreateItemRequest2): Call<CommonResponse>
//
//    @POST("user/dashboard")
//    fun getDashboardFiltered(@Header("x-auth-token") token: String, @Body dashReq: DashBoardReq?): Call<DashBoarData>
//
//    @POST("user/dashboard")
//    fun getDashboard(@Header("x-auth-token") token: String, @Body dashReq: DashBoardReq?): Call<DashBoarData>
//
//    @GET("pricing/plans")
//    fun getPlans(): Call<AllPlanRes>
//
//    @GET("user/my-dashboards")
//    fun getDashCount(@Header("x-auth-token") token: String): Call<DashBoardFIrstRes>
//
//    @GET("teams/roles")
//    fun  getTeamListwithRole(
//        @Header("x-auth-token") token: String
//    ): Call<TeamWithRoleRes>
//
//    @GET("logs/notifications")
//    fun  getNotificationLogs(
//        @Header("x-auth-token") token: String
//    ): Call<InboxListResponse>
//
//    @POST("reach-us/contact")
//    fun doContactUs(@Header("x-auth-token") token: String,@Body conatctUsReq: ContactUsReq?): Call<ContactUsRes>
//
//    @POST("reach-us/feedback")
//    fun dpfeedback(@Header("x-auth-token") token: String,@Body conatctUsReq: FeedbackReq?): Call<feedbackResponse>
//
//    @POST("payment/create-subscription")
//    fun doSubscription(@Header("x-auth-token") token: String,@Body createSubscriptionReq: CreateSubscriptionReq): Call<SubscriptionRes>
//
//    @POST("payment/corporate-subscription")
//    fun doCorperateReq(@Header("x-auth-token") token: String,@Body corperateRq: PaymentActivity.CorperateRq): Call<ResponseRQ>
//
//    @PUT("user")
//    fun doUpdateUser(@Header("x-auth-token") token: String,@Body registerRequest: UserDetailsUploadReq): Call<RegisterRes>
//
//    @PUT("user/update-notify")
//    fun doTurnNotification(@Header("x-auth-token") token: String,@Body updateNoti: UpdateNoti): Call<CommonResponse>
//
//    @DELETE("reimbursements/{reimbursementId}")
//    fun deleteExpense(@Header("x-auth-token") token: String, @Path("reimbursementId") reimbursementId: String): Call<ProjectResponse>
//
//    @DELETE("reimbursements/receipt/{reimbursementId}/")
//    fun deleteReciept(@Header("x-auth-token") token: String, @Path("reimbursementId") reimbursementId: String,@Query("image") image: String): Call<ProjectResponse>
//
//
//    @PUT("logs/notifications/archive/{notificationId}")
//    fun doArchiveNotification(@Header("x-auth-token") token: String, @Path("notificationId") notificationId: String): Call<ContactUsRes>
//
//    @PUT("logs/notifications/read")
//    fun doReadNotifications(@Header("x-auth-token") token: String): Call<ContactUsRes>
//
//    @POST("logs/notifications/archive-messages")
//    fun doArchiveAll(@Header("x-auth-token") token: String,@Body ids: ArchiveDatas): Call<ContactUsRes>
}