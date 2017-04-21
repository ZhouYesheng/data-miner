package le.data.scs.common.entity.meta.tmall;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class TMallSeedEntity implements Serializable{

    private String _tb_token_;

    private String _input_charset;

    private TMallSeedQueryEntity chatContentQuery;

    public TMallSeedEntity(String _tb_token_, String _input_charset, TMallSeedQueryEntity chatContentQuery) {
        this._tb_token_ = _tb_token_;
        this._input_charset = _input_charset;
        this.chatContentQuery = chatContentQuery;
    }

    public TMallSeedEntity() {
    }

    public String get_tb_token_() {
        return _tb_token_;
    }

    public void set_tb_token_(String _tb_token_) {
        this._tb_token_ = _tb_token_;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public TMallSeedQueryEntity getChatContentQuery() {
        return chatContentQuery;
    }

    public void setChatContentQuery(TMallSeedQueryEntity chatContentQuery) {
        this.chatContentQuery = chatContentQuery;
    }
}
