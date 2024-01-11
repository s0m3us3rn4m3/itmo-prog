package objects;

import enums.MediaEnum;
import exceptions.ObjectException;

public class MediaGenerator {
    
    static public Media Generate(String name, MediaEnum mediaType) throws ObjectException {
        switch (mediaType) {
            case NEWSPAPER:
                return new Media(name, mediaType) {
                    @Override
                    public void publish(String news) {
                        System.out.printf("%s напечатала %s\n", this, news);
                    }
                };
            default:
                break;
        }
        throw new ObjectException("MediaType is not supported: " + mediaType.toString());
    }
}
