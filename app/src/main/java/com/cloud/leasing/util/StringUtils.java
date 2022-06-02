package com.cloud.leasing.util;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class StringUtils {

    public static void lengthFilter(final Context context, final EditText editText) {
        InputFilter[] filters = new InputFilter[1];

        filters[0] = new InputFilter.LengthFilter(INPUT_LIMIT_LEN) {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                boolean isChinese = checkNameChese(source.toString());

                // 如果不是中文，或者长度过长就返回“”
                if (!isChinese || dest.toString().length() >= INPUT_LIMIT_LEN) {
                    return "";
                }

                return source;
            }
        };
        // Sets the list of input filters that will be used if the buffer is Editable. Has no effect otherwise.
        editText.setFilters(filters);
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();

        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }

        return res;
    }

    /**
     * 判定输入汉字是否是中文
     *
     * @param c
     */
    public static boolean isChinese(char c) {
        for (char param : chineseParam) {
            if (param == c) {
                return false;
            }
        }

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }

        return false;
    }

    public static final int INPUT_LIMIT_LEN = 20;
    private static char[] chineseParam = new char[]{'」', '，', '。', '？', '…', '：', '～', '【', '＃', '、', '％', '＊', '＆', '＄', '（', '‘', '’',
            '“', '”', '『', '〔', '｛', '【', '￥', '￡', '‖', '〖', '《', '「', '》', '〗', '】', '｝', '〕', '』', '”', '）', '！', '；', '—'};
}
