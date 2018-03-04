package com.github.fo2rist.mclaren.web.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

class StoryStreamContentItem implements Serializable {
    public HashMap<String, String> feed;
    public String title;
    public String source;
    public String story_name;
    public String body;
    public String content_type;
    public String permalink;
    public Date publish_date;
    public Date created_date;
}
