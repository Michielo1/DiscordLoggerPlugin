package me.michielo.discordlogger.util;

import com.pastebin.api.Expiration;
import com.pastebin.api.Format;
import com.pastebin.api.PastebinClient;
import com.pastebin.api.Visibility;
import com.pastebin.api.request.PasteRequest;

public class PastebinUtil {

    public static String createPastebin(String content, String title) {
        final PastebinClient client = PastebinClient
                .builder()
                .developerKey("") //add your own developer key here
                .build();
        final PasteRequest request = PasteRequest
                .content(content)
                .visibility(Visibility.UNLISTED)
                .name(title)
                .expiration(Expiration.NEVER)
                .build();
        // returns the URL of the newly created paste.
        return client.paste(request);
    }

}
