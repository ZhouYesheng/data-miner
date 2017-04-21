package le.data.scs.common.entity.meta.tmall;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TMallCustomerEntity implements Serializable {

    private String _tb_token_;

    private String _input_charset;

    private TMallCustomerQueryEntity queryEntity;

    public TMallCustomerEntity(){}

    public TMallCustomerEntity(String _tb_token_, String _input_charset, TMallCustomerQueryEntity queryEntity) {
        this._tb_token_ = _tb_token_;
        this._input_charset = _input_charset;
        this.queryEntity = queryEntity;
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

    public TMallCustomerQueryEntity getQueryEntity() {
        return queryEntity;
    }

    public void setQueryEntity(TMallCustomerQueryEntity queryEntity) {
        this.queryEntity = queryEntity;
    }
}
