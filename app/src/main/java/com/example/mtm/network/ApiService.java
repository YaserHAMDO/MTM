package com.example.mtm.network;

import com.example.mtm.response.ColumnistsResponse;
import com.example.mtm.response.InternetResponse;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.response.MagazineFullPagesResponse;
import com.example.mtm.response.MediaAgendaResponse;
import com.example.mtm.response.MenuListResponse;
import com.example.mtm.response.NewsPaperFullPagesResponse;
import com.example.mtm.response.NewspaperFirstPagesResponse;
import com.example.mtm.response.SubMenuVisualMediaResponse;
import com.example.mtm.response.TokenResponse;
import com.example.mtm.response.VisualMediaResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("auth/realms/medyatakip.com/protocol/openid-connect/token")
    Call<TokenResponse> getToken(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("auth/realms/medyatakip.com/protocol/openid-connect/logout")
    Call<Void> logout( @Header("Authorization") String authToken, @FieldMap Map<String, String> fields);


    @GET("api/data/agenda/list")
    Call<MediaAgendaResponse> getMediaAgenda(
            @Header("Authorization") String authToken,
            @Query("customerId") int customerId,
            @Query("addHash") boolean addHash,
            @Query("addClip") boolean addClip,
            @Query("addContent") boolean addContent,
            @Query("addDocs") boolean addDocs,
            @Query("addTitle") boolean addTitle,
            @Query("date") String date
    );

    @GET("api/data/pm/first-pages")
    Call<NewspaperFirstPagesResponse> newspaperFirstPages(
            @Header("Authorization") String authToken,
            @Query("customerId") int customerId,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("date") String date
    );


    @GET("api/data/pm/newspaper")
    Call<NewsPaperFullPagesResponse> newspaperFullPages(
            @Header("Authorization") String authToken,
            @Query("customerId") int customerId,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("addImageInfo") boolean addImageInfo,
            @Query("addPages") boolean addPages,
            @Query("distribution") String distribution,
            @Query("mediaType") String mediaType,
            @Query("date") String date
    );

    @GET("api/data/pm/newspaper")
    Call<MagazineFullPagesResponse> magazines(
            @Header("Authorization") String authToken,
            @Query("customerId") int customerId,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("addImageInfo") boolean addImageInfo,
            @Query("addPages") boolean addPages,
            @Query("distribution") String distribution,
            @Query("mediaType") String mediaType,
            @Query("dateStart") String date,
            @Query("dateEnd") String dateEnd
    );

    @GET("api/data/pm/columnists")
    Call<ColumnistsResponse> columnists(
            @Header("Authorization") String authToken,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("customerId") int customerId,
            @Query("addTagMap") boolean addTagMap,
            @Query("dateStart") String dateStart,
            @Query("dateEnd") String dateEnd,
            @Query("ignoreType") String ignoreType,
            @Query("groupByParent") boolean groupByParent,
            @Query("onlyColmnList") boolean onlyColmnList
    );


    @GET("api/data/bc/news-list")
    Call<VisualMediaResponse> visualMedia(
            @Header("Authorization") String authToken,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("customerId") int customerId,
            @Query("addDocs") boolean addDocs,
            @Query("addClipBaseData") boolean addClipBaseData,
            @Query("addDsMenu") boolean addDsMenu,
            @Query("addDsMenuSplitter") boolean addDsMenuSplitter,
            @Query("addDsMenuCount") boolean addDsMenuCount,

            @Query("cdateStart") String cdateStart,
            @Query("cdateEnd") String cdateEnd,
            @Query("ctimeStart") String ctimeStart,
            @Query("ctimeEnd") String ctimeEnd,
            @Query("ignoreType") String ignoreType,

            @Query("groupByParent") boolean groupByParent,
            @Query("addDataGroup") boolean addDataGroup,
            @Query("addSplitter") boolean addSplitter
    );


    @GET("api/data/bc/news-list")
    Call<SubMenuVisualMediaResponse> subMenuVisualMedia(
            @Header("Authorization") String authToken,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("customerId") int customerId,
            @Query("addDocs") boolean addDocs,
            @Query("addClipBaseData") boolean addClipBaseData,
            @Query("addDsMenu") boolean addDsMenu,
            @Query("addDsMenuSplitter") boolean addDsMenuSplitter,
            @Query("addDsMenuCount") boolean addDsMenuCount,
            @Query("addBcProgramData") boolean addBcProgramData,
            @Query("addHash") boolean addHash,
            @Query("addMediaData") boolean addMediaData,
            @Query("addMediaType") boolean addMediaType,
            @Query("addTagMap") boolean addTagMap,
            @Query("cdateStart") String cdateStart,
            @Query("cdateEnd") String cdateEnd,
            @Query("ctimeStart") String ctimeStart,
            @Query("ctimeEnd") String ctimeEnd,
            @Query("clipType") String clipType,
            @Query("ignoreType") String ignoreType,
            @Query("groupByParent") boolean groupByParent,
            @Query("dsMenuId") String dsMenuId,
            @Query("dsSubMenuIds") String dsSubMenuIds,
            @Query("addDataGroup") boolean addDataGroup,
            @Query("addSplitter") boolean addSplitter
    );



    @GET("api/data/dm/news-list")
    Call<InternetResponse> internet(
            @Header("Authorization") String authToken,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("customerId") int customerId,
            @Query("addDocs") boolean addDocs,
            @Query("addClipBaseData") boolean addClipBaseData,
            @Query("addDsMenu") boolean addDsMenu,
            @Query("addDsMenuSplitter") boolean addDsMenuSplitter,
            @Query("addDsMenuCount") boolean addDsMenuCount,

            @Query("cdateStart") String cdateStart,
            @Query("cdateEnd") String cdateEnd,
            @Query("ctimeStart") String ctimeStart,
            @Query("ctimeEnd") String ctimeEnd,
            @Query("ignoreType") String ignoreType,

            @Query("groupByParent") boolean groupByParent,
            @Query("addDataGroup") boolean addDataGroup,
            @Query("addSplitter") boolean addSplitter
    );



    @GET("api/data/dm/news-list")
    Call<InternetSubResponse> subInternet(
            @Header("Authorization") String authToken,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("customerId") int customerId,
            @Query("addDocs") boolean addDocs,
            @Query("addClipBaseData") boolean addClipBaseData,
            @Query("addDsMenu") boolean addDsMenu,
            @Query("addDsMenuSplitter") boolean addDsMenuSplitter,
            @Query("addDsMenuCount") boolean addDsMenuCount,
//            @Query("addBcProgramData") boolean addBcProgramData,
            @Query("addHash") boolean addHash,
            @Query("addMediaData") boolean addMediaData,
            @Query("addMediaType") boolean addMediaType,
            @Query("addTagMap") boolean addTagMap,
            @Query("cdateStart") String cdateStart,
            @Query("cdateEnd") String cdateEnd,
            @Query("ctimeStart") String ctimeStart,
            @Query("ctimeEnd") String ctimeEnd,
//            @Query("clipType") String clipType,
            @Query("ignoreType") String ignoreType,
            @Query("groupByParent") boolean groupByParent,
//            @Query("groupGnos") String groupGnos,
            @Query("dsMenuId") String dsMenuId,
            @Query("dsSubMenuIds") String dsSubMenuIds,
            @Query("addDataGroup") boolean addDataGroup,
            @Query("addSplitter") boolean addSplitter
    );


    @GET("/api/data/pm/news-list")
    Call<MenuListResponse> menuList(
            @Header("Authorization") String authToken,
            @Query("page") int page,
            @Query("perPage") int perPage,
            @Query("customerId") int customerId,
            @Query("addDocs") boolean addDocs,
            @Query("addClipBaseData") boolean addClipBaseData,
            @Query("addDsMenu") boolean addDsMenu,
            @Query("addDsMenuSplitter") boolean addDsMenuSplitter,
            @Query("addDsMenuCount") boolean addDsMenuCount,
            @Query("addImageInfo") boolean addImageInfo,
            @Query("cdateStart") String cdateStart,
            @Query("cdateEnd") String cdateEnd,
            @Query("ctimeStart") String ctimeStart,
            @Query("ctimeEnd") String ctimeEnd,
            @Query("ignoreType") String ignoreType,
            @Query("groupByParent") boolean groupByParent,
            @Query("addDataGroup") boolean addDataGroup,
            @Query("addSplitter") boolean addSplitter,
            @Query("onlyColmnList") boolean onlyColmnList
    );


}
