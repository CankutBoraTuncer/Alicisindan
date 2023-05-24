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
    public static final String KEY_ADVERTISEMENT_IMAGE = "advertisement_image";
    public static final String KEY_ADVERTISEMENT_DESCRIPTION = "advertisement_description";
    public static final String KEY_ADVERTISEMENT_BRAND = "advertisement_brand";
    public static final String KEY_ADVERTISEMENT_TYPE = "advertisement_type";

    public static final String KEY_ADVERTISEMENT_TOKEN = "advertisement_token";

    public static final String KEY_FORUM_ID = "forum_id";
    public static final String KEY_FORUM_OWNER_ID = "forum_owner_id";
    public static final String KEY_FORUM_OWNER_NAME = "forum_owner_name";
    public static final String KEY_FORUM_TITLE = "forum_title";
    public static final String KEY_FORUM_DESCRIPTION = "forum_description";
    public static final String KEY_FORUM_IMAGE = "forum_image";






    public static final ArrayList<String> COUNTRIES = new ArrayList<>(Arrays.asList("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"));
    private static final ArrayList<String> CITY_TURKEY = new ArrayList<>(Arrays.asList("Adana","Adiyaman","Afyon","Agri","Aksaray","Amasya","Ankara","Antalya","Ardahan","Artvin","Aydin","Balikesir","Bartin","Batman","Bayburt","Bilecik","Bingol","Bitlis","Bolu","Burdur","Bursa","Canakkale","Cankiri","Corum","Denizli","Diyarbakir","Duzce","Edirne","Elazig","Erzincan","Erzurum","Eskisehir","Gaziantep","Giresun","Gumushane","Hakkari","Hatay","Igdir","Isparta","Istanbul","Izmir","Kahramanmaras","Karabuk","Karaman","Kars","Kastamonu","Kayseri","Kilis","Kirikkale","Kirklareli","Kirsehir","Kocaeli","Konya","Kutahya","Malatya","Manisa","Mardin","Mersin","Mugla","Mus","Nevsehir","Nigde","Ordu","Osmaniye","Rize","Sakarya","Samsun","Sanliurfa","Siirt","Sinop","Sirnak","Sivas","Tekirdag","Tokat","Trabzon","Tunceli","Usak","Van","Yalova","Yozgat","Zonguldak"));
    public static final ArrayList<ArrayList<String>> CITIES = new ArrayList<>(Arrays.asList(CITY_TURKEY));

    public static final ArrayList<String> CAR_CAR_BRAND = new ArrayList<>(Arrays.asList("IPhone", "Samsung", "Huawei"));
    public static final ArrayList<String> CONDITION = new ArrayList<>(Arrays.asList("Unused", "2nd Hand", "Worn out"));

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
                    "key=AAAAypYZmGI:APA91bG87RuNetFUypTvzUf0wk0diYp-ybIPOjHOxLsTf70ZwyKXY-Ccgd0wpKiJaXRtwhmSH3Huda7G6NxNKma11x9gCXtLXRJNpPf3hAJoIrcXzdxj52osrDT-Pfsdyr7kAo30HTQV"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }


}
