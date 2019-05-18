package com.bpms.sys.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 企业设置实体类
 */
@MappedSuperclass
public class EnterpriseSetting extends SysBaseEntity {
    private static final long serialVersionUID = -20171031153123694L;

    /**
     * 子账号数量
     * 【子账号数量，默认10个】
     */
    @Range(min = 0L, max = 99999999L, message = "请在子账号数量中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_001")
    private Integer setting001 = 10;

    /**
     * 是否允许多公司招聘
     * 【是否允许多公司招聘；0：不支持/1：支持（默认不支持）】
     */
    @Range(min = 0L, max = 99999999L, message = "请在是否允许多公司招聘中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_002")
    private Integer setting002 = 0;

    /**
     * 微信公众号绑定数量
     * 【微信公众号绑定个数，默认1】
     */
    @Range(min = 0L, max = 99999999L, message = "请在微信公众号绑定数量中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_003")
    private Integer setting003 = 0;

    /**
     * 是否允许多部门管理
     */
    @Range(min = 0L, max = 99999999L, message = "请在是否允许多部门管理中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_004")
    private Integer setting004;

    /**
     * 设置005
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置005中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_005")
    private Integer setting005;

    /**
     * 设置006
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置006中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_006")
    private Integer setting006;

    /**
     * 设置007
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置007中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_007")
    private Integer setting007;

    /**
     * 设置008
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置008中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_008")
    private Integer setting008;

    /**
     * 设置009
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置009中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_009")
    private Integer setting009;

    /**
     * 设置010
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置010中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_010")
    private Integer setting010;

    /**
     * 设置011
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置011中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_011")
    private Integer setting011;

    /**
     * 设置012
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置012中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_012")
    private Integer setting012;

    /**
     * 设置013
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置013中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_013")
    private Integer setting013;

    /**
     * 设置014
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置014中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_014")
    private Integer setting014;

    /**
     * 设置015
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置015中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_015")
    private Integer setting015;

    /**
     * 设置016
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置016中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_016")
    private Integer setting016;

    /**
     * 设置017
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置017中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_017")
    private Integer setting017;

    /**
     * 设置018
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置018中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_018")
    private Integer setting018;

    /**
     * 设置019
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置019中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_019")
    private Integer setting019;

    /**
     * 设置020
     */
    @Range(min = 0L, max = 99999999L, message = "请在设置020中输入[0-99999999]范围内的数值。")
    @Column(name = "setting_020")
    private Integer setting020;

    /**
     * 设置021
     */
    @Length(max = 32, message = "请在设置021中输入[0-32]位以内的文字。")
    @Column(name = "setting_021", length = 32)
    private String setting021;

    /**
     * 设置022
     */
    @Length(max = 32, message = "请在设置022中输入[0-32]位以内的文字。")
    @Column(name = "setting_022", length = 32)
    private String setting022;

    /**
     * 设置023
     */
    @Length(max = 32, message = "请在设置023中输入[0-32]位以内的文字。")
    @Column(name = "setting_023", length = 32)
    private String setting023;

    /**
     * 设置024
     */
    @Length(max = 32, message = "请在设置024中输入[0-32]位以内的文字。")
    @Column(name = "setting_024", length = 32)
    private String setting024;

    /**
     * 设置025
     */
    @Length(max = 32, message = "请在设置025中输入[0-32]位以内的文字。")
    @Column(name = "setting_025", length = 32)
    private String setting025;

    /**
     * 设置026
     */
    @Length(max = 32, message = "请在设置026中输入[0-32]位以内的文字。")
    @Column(name = "setting_026", length = 32)
    private String setting026;

    /**
     * 设置027
     */
    @Length(max = 32, message = "请在设置027中输入[0-32]位以内的文字。")
    @Column(name = "setting_027", length = 32)
    private String setting027;

    /**
     * 设置028
     */
    @Length(max = 32, message = "请在设置028中输入[0-32]位以内的文字。")
    @Column(name = "setting_028", length = 32)
    private String setting028;

    /**
     * 设置029
     */
    @Length(max = 32, message = "请在设置029中输入[0-32]位以内的文字。")
    @Column(name = "setting_029", length = 32)
    private String setting029;

    /**
     * 设置030
     */
    @Length(max = 32, message = "请在设置030中输入[0-32]位以内的文字。")
    @Column(name = "setting_030", length = 32)
    private String setting030;

    /**
     * 设置031
     */
    @Length(max = 256, message = "请在设置031中输入[0-256]位以内的文字。")
    @Column(name = "setting_031", length = 256)
    private String setting031;

    /**
     * 设置032
     */
    @Length(max = 256, message = "请在设置032中输入[0-256]位以内的文字。")
    @Column(name = "setting_032", length = 256)
    private String setting032;

    /**
     * 设置033
     */
    @Length(max = 256, message = "请在设置033中输入[0-256]位以内的文字。")
    @Column(name = "setting_033", length = 256)
    private String setting033;

    /**
     * 设置034
     */
    @Length(max = 256, message = "请在设置034中输入[0-256]位以内的文字。")
    @Column(name = "setting_034", length = 256)
    private String setting034;

    /**
     * 设置035
     */
    @Length(max = 256, message = "请在设置035中输入[0-256]位以内的文字。")
    @Column(name = "setting_035", length = 256)
    private String setting035;

    /**
     * 设置036
     */
    @Length(max = 256, message = "请在设置036中输入[0-256]位以内的文字。")
    @Column(name = "setting_036", length = 256)
    private String setting036;

    /**
     * 设置037
     */
    @Length(max = 256, message = "请在设置037中输入[0-256]位以内的文字。")
    @Column(name = "setting_037", length = 256)
    private String setting037;

    /**
     * 设置038
     */
    @Length(max = 256, message = "请在设置038中输入[0-256]位以内的文字。")
    @Column(name = "setting_038", length = 256)
    private String setting038;

    /**
     * 设置039
     */
    @Length(max = 256, message = "请在设置039中输入[0-256]位以内的文字。")
    @Column(name = "setting_039", length = 256)
    private String setting039;

    /**
     * 设置040
     */
    @Length(max = 256, message = "请在设置040中输入[0-256]位以内的文字。")
    @Column(name = "setting_040", length = 256)
    private String setting040;

    /**
     * 设置041
     */
    @Length(max = 1024, message = "请在设置041中输入[0-1024]位以内的文字。")
    @Column(name = "setting_041", length = 1024)
    private String setting041;

    /**
     * 设置042
     */
    @Length(max = 1024, message = "请在设置042中输入[0-1024]位以内的文字。")
    @Column(name = "setting_042", length = 1024)
    private String setting042;

    /**
     * 设置043
     */
    @Length(max = 1024, message = "请在设置043中输入[0-1024]位以内的文字。")
    @Column(name = "setting_043", length = 1024)
    private String setting043;

    /**
     * 设置044
     */
    @Length(max = 1024, message = "请在设置044中输入[0-1024]位以内的文字。")
    @Column(name = "setting_044", length = 1024)
    private String setting044;

    /**
     * 设置045
     */
    @Length(max = 1024, message = "请在设置045中输入[0-1024]位以内的文字。")
    @Column(name = "setting_045", length = 1024)
    private String setting045;

    /**
     * 设置046
     */
    @Column(name = "setting_046")
    private String setting046;

    /**
     * 设置047
     */
    @Column(name = "setting_047")
    private String setting047;

    /**
     * 设置048
     */
    @Column(name = "setting_048")
    private String setting048;

    /**
     * 设置049
     */
    @Column(name = "setting_049")
    private String setting049;

    /**
     * 设置050
     */
    @Column(name = "setting_050")
    private String setting050;

    public EnterpriseSetting() {
    }

    public Integer getSetting001() {
        return this.setting001;
    }

    public void setSetting001(Integer setting001) {
        this.setting001 = setting001;
    }

    public Integer getSetting002() {
        return this.setting002;
    }

    public void setSetting002(Integer setting002) {
        this.setting002 = setting002;
    }

    public Integer getSetting003() {
        return this.setting003;
    }

    public void setSetting003(Integer setting003) {
        this.setting003 = setting003;
    }

    public Integer getSetting004() {
        return this.setting004;
    }

    public void setSetting004(Integer setting004) {
        this.setting004 = setting004;
    }

    public Integer getSetting005() {
        return this.setting005;
    }

    public void setSetting005(Integer setting005) {
        this.setting005 = setting005;
    }

    public Integer getSetting006() {
        return this.setting006;
    }

    public void setSetting006(Integer setting006) {
        this.setting006 = setting006;
    }

    public Integer getSetting007() {
        return this.setting007;
    }

    public void setSetting007(Integer setting007) {
        this.setting007 = setting007;
    }

    public Integer getSetting008() {
        return this.setting008;
    }

    public void setSetting008(Integer setting008) {
        this.setting008 = setting008;
    }

    public Integer getSetting009() {
        return this.setting009;
    }

    public void setSetting009(Integer setting009) {
        this.setting009 = setting009;
    }

    public Integer getSetting010() {
        return this.setting010;
    }

    public void setSetting010(Integer setting010) {
        this.setting010 = setting010;
    }

    public Integer getSetting011() {
        return this.setting011;
    }

    public void setSetting011(Integer setting011) {
        this.setting011 = setting011;
    }

    public Integer getSetting012() {
        return this.setting012;
    }

    public void setSetting012(Integer setting012) {
        this.setting012 = setting012;
    }

    public Integer getSetting013() {
        return this.setting013;
    }

    public void setSetting013(Integer setting013) {
        this.setting013 = setting013;
    }

    public Integer getSetting014() {
        return this.setting014;
    }

    public void setSetting014(Integer setting014) {
        this.setting014 = setting014;
    }

    public Integer getSetting015() {
        return this.setting015;
    }

    public void setSetting015(Integer setting015) {
        this.setting015 = setting015;
    }

    public Integer getSetting016() {
        return this.setting016;
    }

    public void setSetting016(Integer setting016) {
        this.setting016 = setting016;
    }

    public Integer getSetting017() {
        return this.setting017;
    }

    public void setSetting017(Integer setting017) {
        this.setting017 = setting017;
    }

    public Integer getSetting018() {
        return this.setting018;
    }

    public void setSetting018(Integer setting018) {
        this.setting018 = setting018;
    }

    public Integer getSetting019() {
        return this.setting019;
    }

    public void setSetting019(Integer setting019) {
        this.setting019 = setting019;
    }

    public Integer getSetting020() {
        return this.setting020;
    }

    public void setSetting020(Integer setting020) {
        this.setting020 = setting020;
    }

    public String getSetting021() {
        return this.setting021;
    }

    public void setSetting021(String setting021) {
        this.setting021 = setting021;
    }

    public String getSetting022() {
        return this.setting022;
    }

    public void setSetting022(String setting022) {
        this.setting022 = setting022;
    }

    public String getSetting023() {
        return this.setting023;
    }

    public void setSetting023(String setting023) {
        this.setting023 = setting023;
    }

    public String getSetting024() {
        return this.setting024;
    }

    public void setSetting024(String setting024) {
        this.setting024 = setting024;
    }

    public String getSetting025() {
        return this.setting025;
    }

    public void setSetting025(String setting025) {
        this.setting025 = setting025;
    }

    public String getSetting026() {
        return this.setting026;
    }

    public void setSetting026(String setting026) {
        this.setting026 = setting026;
    }

    public String getSetting027() {
        return this.setting027;
    }

    public void setSetting027(String setting027) {
        this.setting027 = setting027;
    }

    public String getSetting028() {
        return this.setting028;
    }

    public void setSetting028(String setting028) {
        this.setting028 = setting028;
    }

    public String getSetting029() {
        return this.setting029;
    }

    public void setSetting029(String setting029) {
        this.setting029 = setting029;
    }

    public String getSetting030() {
        return this.setting030;
    }

    public void setSetting030(String setting030) {
        this.setting030 = setting030;
    }

    public String getSetting031() {
        return this.setting031;
    }

    public void setSetting031(String setting031) {
        this.setting031 = setting031;
    }

    public String getSetting032() {
        return this.setting032;
    }

    public void setSetting032(String setting032) {
        this.setting032 = setting032;
    }

    public String getSetting033() {
        return this.setting033;
    }

    public void setSetting033(String setting033) {
        this.setting033 = setting033;
    }

    public String getSetting034() {
        return this.setting034;
    }

    public void setSetting034(String setting034) {
        this.setting034 = setting034;
    }

    public String getSetting035() {
        return this.setting035;
    }

    public void setSetting035(String setting035) {
        this.setting035 = setting035;
    }

    public String getSetting036() {
        return this.setting036;
    }

    public void setSetting036(String setting036) {
        this.setting036 = setting036;
    }

    public String getSetting037() {
        return this.setting037;
    }

    public void setSetting037(String setting037) {
        this.setting037 = setting037;
    }

    public String getSetting038() {
        return this.setting038;
    }

    public void setSetting038(String setting038) {
        this.setting038 = setting038;
    }

    public String getSetting039() {
        return this.setting039;
    }

    public void setSetting039(String setting039) {
        this.setting039 = setting039;
    }

    public String getSetting040() {
        return this.setting040;
    }

    public void setSetting040(String setting040) {
        this.setting040 = setting040;
    }

    public String getSetting041() {
        return this.setting041;
    }

    public void setSetting041(String setting041) {
        this.setting041 = setting041;
    }

    public String getSetting042() {
        return this.setting042;
    }

    public void setSetting042(String setting042) {
        this.setting042 = setting042;
    }

    public String getSetting043() {
        return this.setting043;
    }

    public void setSetting043(String setting043) {
        this.setting043 = setting043;
    }

    public String getSetting044() {
        return this.setting044;
    }

    public void setSetting044(String setting044) {
        this.setting044 = setting044;
    }

    public String getSetting045() {
        return this.setting045;
    }

    public void setSetting045(String setting045) {
        this.setting045 = setting045;
    }

    public String getSetting046() {
        return this.setting046;
    }

    public void setSetting046(String setting046) {
        this.setting046 = setting046;
    }

    public String getSetting047() {
        return this.setting047;
    }

    public void setSetting047(String setting047) {
        this.setting047 = setting047;
    }

    public String getSetting048() {
        return this.setting048;
    }

    public void setSetting048(String setting048) {
        this.setting048 = setting048;
    }

    public String getSetting049() {
        return this.setting049;
    }

    public void setSetting049(String setting049) {
        this.setting049 = setting049;
    }

    public String getSetting050() {
        return this.setting050;
    }

    public void setSetting050(String setting050) {
        this.setting050 = setting050;
    }

}