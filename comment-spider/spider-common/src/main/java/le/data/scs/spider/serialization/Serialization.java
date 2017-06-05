package le.data.scs.spider.serialization;

/**
 * Created by young.yang on 2017/2/25.
 * 对象序列化
 */
public interface Serialization<from,to> {
    /**
     * 序列化对象
     * @param f
     * @return
     * @throws SerializationException
     */
    public to serialization(from f) throws SerializationException;

    /**
     * 反序列化对象
     * @param t
     * @param fromClass
     * @return
     * @throws SerializationException
     */
    public from unserialization(to t, Class<from> fromClass)  throws SerializationException;
}
