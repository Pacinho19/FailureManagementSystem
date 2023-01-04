package pl.pacinho.failuremanagementsystem.utils;

import com.github.slugify.Slugify;


public class SlugifyUtils {

    public static String slugify(String slug) {
        return new Slugify()
                .slugify(slug);
    }
}
