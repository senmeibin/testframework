package com.bpms.sys.entity.ext;

import com.bpms.sys.entity.Dictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_dictionary")
public class DictionaryExt extends Dictionary {
    private static final long serialVersionUID = -20160331132445350L;
}