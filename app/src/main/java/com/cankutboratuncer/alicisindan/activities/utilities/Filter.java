package com.cankutboratuncer.alicisindan.activities.utilities;

public class Filter {
    private String attribute_location;
    private String attribute_type;
    private String attribute_category;
    private String attribute_brand;
    private String attribute_color;
    private String filter_sort_by;
    // TODO: price range

    public Filter(String attribute_location,
                  String attribute_type,
                  String attribute_category,
                  String attribute_brand,
                  String attribute_color,
                  String filter_sort_by) {
        this.attribute_location = attribute_location;
        this.attribute_type = attribute_type;
        this.attribute_category = attribute_category;
        this.attribute_brand = attribute_brand;
        this.attribute_color = attribute_color;
        this.filter_sort_by = filter_sort_by;
    }

    // TODO: if required, implement getters & setters
}
