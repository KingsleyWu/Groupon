package com.kingsley.groupon.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/21 21:44
 * file change date : on 2017/6/21 21:44
 * version: 1.0
 */

public class PinYinUtil {
    public static String getPinYin(String name) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyin.length > 0) {
                    sb.append(pinyin[0]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return sb.toString();
    }

}
