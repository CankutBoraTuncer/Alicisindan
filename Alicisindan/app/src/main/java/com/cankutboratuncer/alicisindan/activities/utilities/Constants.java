package com.cankutboratuncer.alicisindan.activities.utilities;
import com.cankutboratuncer.alicisindan.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_COLLECTION_ADVERTISEMENTS = "advertisements";
    public static final String KEY_COLLECTION_ADVERTISEMENT_CHAT = "advertisement_chat";
    public static final String KEY_COLLECTION_FORUM_POSTS = "forumPosts";
    public static final String KEY_COLLECTION_FORUM_CHAT = "forum_chat";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_SURNAME = "userSurname";
    public static final String KEY_USER_USERNAME = "userUsername";
    public static final String KEY_USER_ADDRESS = "userAddress";
    public static final String KEY_USER_PHONE = "userPhone";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER_IMAGE = "userImage";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_IS_USER_SKIP = "isSkip";
    public static final String KEY_ADVERTISEMENT = "advertisement";
    public static final String KEY_ADVERTISEMENT_TITLE = "advertisement_title";
    public static final String KEY_ADVERTISEMENT_USERID = "advertisement_userid";
    public static final String KEY_ADVERTISEMENT_USERNAME = "advertisement_username";
    public static final String KEY_ADVERTISEMENT_ID = "advertisement_id";
    public static final String KEY_ADVERTISEMENT_LOCATION = "advertisement_location";
    public static final String KEY_ADVERTISEMENT_PRICE = "advertisement_price";
    public static final String KEY_ADVERTISEMENT_PREVIEW_IMAGE = "advertisement_preview_image";
    public static final String KEY_ADVERTISEMENT_IMAGE = "advertisement_image";
    public static final String KEY_ADVERTISEMENT_DESCRIPTION = "advertisement_description";
    public static final String KEY_ADVERTISEMENT_BRAND = "advertisement_brand";
    public static final String KEY_ADVERTISEMENT_TYPE = "advertisement_type";
    public static final String KEY_ADVERTISEMENT_CATEGORY= "advertisement_category";
    public static final String KEY_ADVERTISEMENT_CONDITION = "advertisement_condition";

    public static final String KEY_ADVERTISEMENT_TOKEN = "advertisement_token";

    public static final String KEY_FORUM_ID = "forum_id";
    public static final String KEY_FORUM_OWNER_ID = "forum_owner_id";
    public static final String KEY_FORUM_OWNER_NAME = "forum_owner_name";
    public static final String KEY_FORUM_TITLE = "forum_title";
    public static final String KEY_FORUM_DESCRIPTION = "forum_description";
    public static final String KEY_FORUM_IMAGE = "forum_image";

    private static final ArrayList<String> CITY_TURKEY = new ArrayList<>(Arrays.asList("Istanbul","Ankara","Izmir","Bursa","Antalya","Konya","Gaziantep","Sanliurfa","Mersin","Adana","Diyarbakir","Kayseri","Samsun","Akcaabat","Denizli","Cankaya","Esenyurt","Eskisehir","Seyhan","Bagcilar","Kucukcekmece","Erzurum","Pendik","Selcuklu","Yildirim","Kartal","Kagithane","Esenler","Batman","Avcilar","Kahramanmaras","Trabzon","Malatya","Elazig","Yuregir","Hatay","Sivas","Gebze","Kocaeli","Kayapinar","Konak","Manisa","Van","Pamukkale","Sariyer","Meram","Tarsus","Sultanbeyli","Beylikduzu","Balikesir","Gungoren","Aksaray","Alanya","Yesilyurt","Adiyaman","Afyonkarahisar","Iskenderun","Battalgazi","Corum","Corlu","Sakarya","Arnavutkoy","Inegol","Kutahya","Osmaniye","Tuzla","Isparta","Siverek","Cekme","Kiziltepe","Usak","Buyukcekmece","Beykoz","Duzce","Manavgat","Ordu","Bolu","Tekirdag","Tokat","Viransehir","Karakopru","Karaman","Mus","Kirikkale","Aydin","Canakkale","Edirne","Torbali","Eregli","Bodrum","Menemen","Akhisar","Talas","Cerkezkoy","Siirt","Korfez","Turgutlu","Golcuk","Salihli","Ceyhan","Bingol","Fethiye","Erzincan","Nazilli","Bartin","Edremit","Bandirma","Kirsehir","Nevsehir","Agri","Amasya","Kastamonu","Luleburgaz","Yalova","Eregli","Cizre","Elbistan","Nizip","Rize","Carsamba","Silivri","Igdir","Giresun","Silopi","Odemis","Karabuk","Kozan","Ergani","Cayirova","Unye","Polatli","Serik","Kadirli","Patnos","Kahta","Samandag","Dogubayazit","Zonguldak","Soke","Yuksekova","Silifke","Karatepe","Nigde","Kapakli","Fatsa","Kilis","Kars","Kusadasi","Midyat","Akcakale","Burdur","Soma","Kemalpasa","Nusaybin","Alasehir","Yozgat","Suruc","Bergama","Tavsanli","Cankiri","Sirnak","Mardin","Bilecik","Hakkari","Bitlis","Bayburt","Kirklareli","Gumushane","Mugla","Ardahan","Tunceli","Artvin","Sinop","Reyhanli","Asagicinik","Bafra","Aliaga","Erbaa","Birecik","Marmaris","Menderes","Aksehir","Ercis","Tatvan","Caycuma","Biga","Arsuz","Akyazi","Ceylanpinar","Tire","Afsin","Bulanik","Turhal","Sorgun","Idil","Bozuyuk","Cinar","Turkoglu","Beysehir","Belek","Catalca","Merzifon","Ayvalik","Islahiye","Bulancak","Cumra","Urla","Anamur","Develi","Bucak","Seydisehir","Niksar","Simav","Caldiran","Mut","Derik","Uzunkopru","Kozluk","Burhaniye","Civril","Bor","Kas","Hassa","Zile","Acipayam","Saruhanli","Kizilpinar","Malkara","Baskale","Cihanbeyli","Tekkekoy","Incirliova","Cermik","Bigadic","Gediz","Cine","Karapinar","Arakli","Saray","Sungurlu","Kozlu","Babaeski","Beypazari","Can","Finike","Guroymak","Suluova","Yalvac","Haymana","Yatagan","Seferhisar","Alapli","Cayeli","Dikili","Boyabat","Tavas","Of","Cesme","Bolvadin","Kemer","Akdagmadeni","Sarikamis","Diyadin","Yakacik","Sinanpasa","Erzin","Halfeti","Ardesen","Ahlat","Bayindir","Ilica","Tosya","Havza","Kirkagac","Yomra","Dogansehir","Ciftlikkoy","Susurluk","Akcakoca","Taskopru","Genc","Sarkisla","Dicle","Emirdag","Besni","Kaman","Suhut","Kulp","Sile","Selcuk","Banaz","Yahyali","Kurtalan","Sarigol","Gurpinar","Kilimli","Hamidiye","Yerkoy","Yayladagi","Urgup","Akcadag","Esme","Arsin","Espiye","Cinarcik","Sereflikochisar","Bogazliyan","Sindirgi","Bozdogan","Belen","Ayvacik","Golbasi","Persembe","Andirin","Kadinhani","Sarikaya","Araban","Oguzeli","Avanos","Sarkoy","Foca","Pervari","Mutki","Tirebolu","Yenice","Ortakoy","Gallipoli","Yildizeli","Bunyan","Cay","Yahsihan","Varto","Saraykoy","Besiri","Adilcevaz","Tasova","Kinik","Bayramic","Karakocan","Gevas","Pazarcik","Ihsaniye","Almus","Altinova","Vakfikebir","Ciftlik","Nallihan","Havran","Ipsala","Surmene","Savur","Buldan","Pinarbasi","Sarayonu","Bozyazi","Ulubey","Macka","Ondokuzmayis","Incesu","Bozkir","Baykan","Lice","Tekman","Marmara Ereglisi","Demre","Alacam","Korkut","Haskoy","Harbiye","Sarkikaraagac","Kocaali","Borcka","Susehri","Ula","Kovancilar","Gole","Gemerek","Karatas","Tuzluca","Hopa","Palu","Ayancik","Askale","Gerede","Caglayancerit","Akkus","Cide","Ulukisla","Selim","Yunak","Alaattin","Aybasti","Tomarza","Besikduzu","Sirvan","Golhisar","Ulus","Bahce","Digor","Yavuzeli","Gumushacikoy","Ayvacik","Gulsehir","Zara","Gokcebey","Kavak","Kangal","Yenice","Aralik","Karaagac","Osmaneli","Derinkuyu","Sivasli","Sivrihisar","Pozanti","Arhavi","Sultanhisar","Eruh","Baskil","Taslicay","Dereli","Cilimli","Selendi","Toprakkale","Siran","Emet","Cekerek","Duragan","Acigol","Yusufeli","Yumurtalik","Sogut","Cal","Gulagac","Arac","Cerkes","Keskin","Denizciler","Senkaya","Savastepe","Asarcik","Tufanbeyli","Savsat","Yesilhisar","Cat","Arpacay","Cukurca","Tortum","Aricak","Pelitli","Sarioglan","Findikli","Altintas","Yaglidere","Turgutreis","Aladag","Ahmetli","Carsibasi","Bayat","Divrigi","Narlica","Caykara","Koprukoy","Semdinli","Huyuk","Doganhisar","Turkeli","Mecitozu","Yigilca","Ayas","Altunhisar","Yesilova","Ezine","Serinyol","Catalpinar","Domanic","Sefaatli","Cifteler","Sultandagi","Ozdere","Dargecit","Lapseki","Cumayeri","Amasra","Piraziz","Cicekdagi","Bahcesaray","Tonya","Altinekin","Cobanlar","Serinhisar","Omerli","Pazar","Uzumlu","Sumbas","Ortahisar","Saraykent","Eynesil","Sogutlu","Solhan","Caybasi","Altinoluk","Gomec","Torul","Konakli","Cayiralan","Seyitgazi","Camardi","Hanimciftligi","Guclukonak","Mazidagi","Balya","Cavdir","Akseki","Ortaklar","Ulubey","Bozkurt","Buharkent","Sason","Yenipazar","Koyulhisar","Sariveliler","Cukurcayir","Beydag","Hisarcik","Uludere","Refahiye","Hizan","Nurhak","Alucra","Cungus","Atca","Erfelek","Camas","Hadim","Kargipinar","Salpazari","Sultanhani","Senirkent","Umurlu","Ortakent","Kuzuculu","Ermenek","Derecik","Alpu","Yakinca","Aydincik","Side","Acarlar","Akyaka","Kucuk Dalyan","Yesilli","Karaagac","Tomuk","Buyukyoncali","Enez","Arapgir","Karayilan","Yesilkoy","Sivrice","Sutculer","Goynucek","Inebolu","Kucukkuyu","Golpazari","Pazaryeri","Susuz","Karahalli","Karkamis","Aydincik","Kursunlu","Erenler","Eleskirt","Tefenni","Tut","Davutlar","Kumcati","Sariz","Celtik","Buyukorhan","Basmakci","Turgutalp","Yenice","Alacati","Cildir","Yazikonak","Gokceada","Camoluk","Harran","Ulas","Armutlu","Kose","Adakli","Altinyayla","Aslanapa","Yesilyurt","Cardak","Yakakent","Beldibi","Cayirhan","Karliova","Cayirli","Yaprakli","Catalagzi","Emirgazi","Kulu","Eceabat","Ilic","Ortakoy","Camliyayla","Demirozu","Aglasun","Sulakyurt","Avsallar"));
    public static final ArrayList<ArrayList<String>> CITIES = new ArrayList<>(Arrays.asList(CITY_TURKEY));
    public static final ArrayList<String> COUNTRIES = new ArrayList<>(Arrays.asList("country","Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antigua and Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bonaire, Sint Eustatius, and Saba","Bosnia and Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Côte d'Ivoire","Cabo Verde","Cambodia","Cameroon","Canada","Cayman Islands","Central African Republic","Chad","Chile","China","Christmas Island","Colombia","Comoros","Congo (Brazzaville)","Congo (Kinshasa)","Cook Islands","Costa Rica","Croatia","Cuba","Curaçao","Cyprus","Czechia","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands (Islas Malvinas)","Faroe Islands","Federated States of Micronesia","Fiji","Finland","France","French Guiana","French Polynesia","Gabon","Gaza Strip","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle Of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kiribati","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","North Korea","Northern Mariana Islands","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn Islands","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Barthelemy","Saint Helena, Ascension, and Tristan da Cunha","Saint Kitts and Nevis","Saint Lucia","Saint Martin","Saint Pierre and Miquelon","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Sint Maarten","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Georgia And South Sandwich Islands","South Georgia and South Sandwich Islands","South Korea","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Svalbard","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","The Bahamas","The Gambia","Timor-Leste","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","U.S. Virgin Islands","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Wallis and Futuna","West Bank","Yemen","Zambia","Zimbabwe"));

    public static final ArrayList<String> SORTING_METHODS = new ArrayList<>(Arrays.asList("NewestFirst", "OldestFirst", "CheapFirst", "ExpensiveFirst"));
    public static final ArrayList<String> CONDITION_FILTER = new ArrayList<>(Arrays.asList("Any", "Unused", "2nd Hand", "Worn out"));
    public static final ArrayList<String> CONDITION_POST = new ArrayList<>(Arrays.asList("Unused", "2nd Hand", "Worn out"));


    public static final String KEY_USER_ID = "userId";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";

    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_SENDER_USERNAME = "senderUserName";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";

    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final ArrayList<String> categories = new ArrayList<>(Arrays.asList("Cars","Telephone", "House Appliance", "Electronics", "Motorcycle", "Other Vehicles", "Baby & Children Care", "Sports and Outdoors", "Hobbies and Entertainment", "Clothes and Accessories", "Stationery", "Books and Literature"));
    public static final ArrayList<String> carCategory = new ArrayList<>(Arrays.asList("Cars","Car Spare Parts and Accessories", "Car Audio and Video Systems", "Wheel and Tire", "Rental Vehicles"));
    public static final ArrayList<String> telephoneCategory = new ArrayList<>(Arrays.asList("Smart Phone", "Phone Accessories and Parts", "Other"));
    public static final ArrayList<String> houseApplianceCategory = new ArrayList<>(Arrays.asList("Furniture", "Kitchenware and Tableware", "Decoration", "Garden", "Hand Tools", "Other"));
    public static final ArrayList<String> electronicsCategory = new ArrayList<>(Arrays.asList("Desktop Computer", "Laptop", "Cameras", "Tablets", "Headphones", "TVs", "Smart Watches", "Gaming"));
    public static final ArrayList<String> motorcycleCategory = new ArrayList<>(Arrays.asList("Motorcycle", "Motorcycle Parts", "Helmet", "Accessories"));
    public static final ArrayList<String> otherVehiclesCategory = new ArrayList<>(Arrays.asList("Truck", "Minivan and Panelvan", "Caravan", "Other Vehicle Parts", "Other"));
    public static final ArrayList<String> babyChildrenCategory = new ArrayList<>(Arrays.asList("Hygiene", "Clothes", "Baby & Children Furniture"));
    public static final ArrayList<String> sportsOutdoorCategory = new ArrayList<>(Arrays.asList("Bicycle", "Exercise Equipment", "Sports equipment","Other"));
    public static final ArrayList<String> hobbiesCategory = new ArrayList<>(Arrays.asList("Movie and Music", "Musical Instruments", "Board and Board Games","Pet Products","Other"));
    public static final ArrayList<String> clothesCategory = new ArrayList<>(Arrays.asList("Tops", "Pants", "Jeans", "Dresses", "Coats", "Skirts", "Shoes", "Accessories"));
    public static final ArrayList<String> stationaryCategory = new ArrayList<>(Arrays.asList("Pen and Pencil", "Notebooks", "Erasers & Lead", "Other"));
    public static final ArrayList<String> booksCategory = new ArrayList<>(Arrays.asList("University books", "Preschool books", "Other"));
    public static final ArrayList<Integer> categoryImages = new ArrayList<>(Arrays.asList(R.drawable.img_car, R.drawable.img_phone, R.drawable.img_sofa, R.drawable.img_pc, R.drawable.img_motorcycle, R.drawable.img_truck, R.drawable.img_baby, R.drawable.img_sport, R.drawable.img_music, R.drawable.img_shoe, R.drawable.img_book, R.drawable.img_pen));

    public static ArrayList<String> findSubCategory(String category){
        if(category.equals("Cars")){
            return carCategory;
        }
        else if (category.equals("Telephone")){
            return telephoneCategory;
        }
        else if (category.equals("House Appliance")){
            return houseApplianceCategory;
        }
        else if (category.equals("Electronics")){
            return electronicsCategory;
        }
        else if (category.equals("Motorcycle")){
            return motorcycleCategory;
        }
        else if (category.equals("Other Vehicles"))
        {
            return otherVehiclesCategory;
        }
        else if (category.equals("Baby & Children Care"))
        {
            return babyChildrenCategory;
        }
        else if (category.equals("Sports and Outdoors"))
        {
            return sportsOutdoorCategory;
        }
        else if (category.equals("Hobbies and Entertainment"))
        {
            return hobbiesCategory;
        }
        else if (category.equals("Clothing and Accessories"))
        {
            return clothesCategory;
        }
        else if (category.equals("Stationery"))
        {
            return stationaryCategory;
        }
        else if (category.equals("Books and Literature"))
        {
            return booksCategory;
        }
        else {
            return null;
        }
    }

    public static HashMap<String, String> remoteMsgHeaders = null;

    public static HashMap<String, String> getRemoteMsgHeaders() {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAA-HtNnc8:APA91bHu6WOILREkyi9nv1ocQXuaYuTq1WN6rvM9EgyBuU2kX7d5Pk_1oy3bVxe8V9YtFsJvx78AesnipkPvB2lEMWOtPAcPTQ4mtTXwjrXu7OKYjcSf_ARZBF_d-H7eGehOnL2BUvgV"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }


}
