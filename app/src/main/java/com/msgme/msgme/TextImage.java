
package com.msgme.msgme;


import java.lang.StringBuilder;
import java.net.URLEncoder;
import java.util.Locale;

import com.msgme.msgme.data.IconsDataProvider;
import com.msgme.msgme.data.LogoDataProvider;
import com.msgme.msgme.vo.IconData;
import com.msgme.msgme.vo.LogoData;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;


public class TextImage implements TextWatcher, OnLongClickListener {

    private enum Action {
        /*
         * DEL_CHAR MUST be the first del, all other option should be smaller then DEL_*
         */
        KEY_PRESSED(0), PASTE(1), COPY(2), DEL_CHAR(3), DEL_IMG(4), DEL_LINK(5);

        private int _val;

        Action(int val) {
            this._val = val;
        }

        /*
         * use this to compare different values ( <, <=, >, >= )
         */
        public int getVal() {
            return _val;
        }

    }

    ;

    public final static int GOOGLE_YT_COLOR = 0xff0000fe;
    public final static int WAZE_COLOR = Color.BLUE;

    private final static String HTML_GOOGLE_COLOR = String.format("%x", GOOGLE_YT_COLOR).substring(2, 8);
    private final static String HTML_WAZE_COLOR = String.format("%x", WAZE_COLOR).substring(2, 8);
    private final static String HTML_IMG_SRC_TAG = "<img src=\"";
    private final static String HTML_COLOR_END_TAG = "</font>";
    private final static String[][] HTML_COLOR_TAG = {{HTML_COLOR_END_TAG, "]}"},
            {"<font color =\"#" + HTML_GOOGLE_COLOR + "\">", "{["},
            {"<font color =\"#" + HTML_WAZE_COLOR + "\">", "{]"}
    };

    private final String REGISTER_LINK_URL = "http://smartxt.me/default.aspx?id=";
    private final String YT_SEARCH = "http://www.youtube.com/results?search_query=";
    private final String GOOGLE_SEARCH_URL = "http://google.com/search?q=";

    private final String ERROR_OPEN_GOOGLE = "can't open google search with wanted phrase";
    private final String ERROR_OPEN_YT = "can't open youtube with wanted phrase";
    private final String ERROR_OPEN_WAZE = "can't open waze or play store search with wanted phrase";

    private EditText _etMessage = null;
    private Editable _lastMsgState = null;
    private TextView _tvDisplayMessage = null;
    public Context _context = null;


    private boolean _userClick = true;                //if the vent if due to user click (true) or setText (false)
    private boolean _onLongClickState = false;        //if component in long click (don't delete selection if word is
    // underline)
    private CharSequence _textToInsert = "";            //if it's not empty ("") - the Del was pressed and icon was
    // deleted - this field can't be null, use "" instead
    private int _lastChangeInx = 0;
    private Action _lastAction = Action.KEY_PRESSED;    //last action user made
    private Action _lastDelAction = Action.DEL_CHAR;    //last delete action user made
    private int _pasteLen = 0;
    private ForegroundColorSpan[] _colorSpansEffacted = null;
    private ClickableSpan[] _linkSpansEffacted = null;
    private ClickableSpan[] _textSpansEffacted = null;
    private int[] _textSpansEffactedEnd = null;

    public TextImage(View etMessageBody, Context context) {
        if (etMessageBody.getClass().equals(EditText.class)) {
            _etMessage = (EditText) etMessageBody;
            _etMessage.addTextChangedListener(this);
            _etMessage.setOnLongClickListener(this);

        }
        if (etMessageBody.getClass().equals(TextView.class)) {
            _tvDisplayMessage = (TextView) etMessageBody;
            _tvDisplayMessage.setMovementMethod(LinkMovementMethod.getInstance());
        }
        _context = context;


    }

    public void init() {
        _userClick = true;                //if the vent if due to user click (true) or setText (false)
        _onLongClickState = false;        //if component in long click (don't delete selection if word is underline)
        _textToInsert = "";            //if it's not empty ("") - the Del was pressed and icon was deleted - this
        // field can't be null, use "" instead
        _lastChangeInx = 0;
        _lastAction = Action.KEY_PRESSED;    //last action user made
        _lastDelAction = Action.DEL_CHAR;    //last delete action user made
        _pasteLen = 0;

        _colorSpansEffacted = null;
        _linkSpansEffacted = null;
        _textSpansEffacted = null;
        _textSpansEffactedEnd = null;

        setText(null, 0);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _lastChangeInx = start;

        if ((!_userClick) || (_onLongClickState))
            return;

        if (_textSpansEffacted != null) {
            for (int i = 0; i < _textSpansEffacted.length; i++) {
                _etMessage.getText().setSpan(_textSpansEffacted[i], start + count, _textSpansEffactedEnd[i] + (count
                        - before), 0);
            }
            _textSpansEffacted = null;
            _textSpansEffactedEnd = null;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        if (!_userClick)
            return;

        _pasteLen = 0;

        _lastMsgState = ((Editable) s);
        _colorSpansEffacted = _lastMsgState.getSpans(start, start + count, ForegroundColorSpan.class);
        _linkSpansEffacted = _lastMsgState.getSpans(start, start + count, ClickableSpan.class);

        if ((start < s.length()) || (start + count < s.length())) {
            _textSpansEffacted = _lastMsgState.getSpans(start, start + count + after, ClickableSpan.class);
            _textSpansEffactedEnd = new int[_textSpansEffacted.length];
            for (int i = 0; i < _textSpansEffacted.length; i++) {
                _textSpansEffactedEnd[i] = _etMessage.getText().getSpanEnd(_textSpansEffacted[i]);
                _etMessage.getText().removeSpan(_textSpansEffacted[i]);
            }
        }

        if (after > 1) {
            if (_lastChangeInx != start) {
                _lastAction = Action.PASTE;
                _pasteLen = after;
            } else {
                _lastAction = Action.KEY_PRESSED;
                return;
            }
        } else if (count == 0 && after == 1) {
            _lastAction = Action.KEY_PRESSED;
            _colorSpansEffacted = null;
            _linkSpansEffacted = null;
        }

        if (after != 0)    //if "Del" wasn't pressed
            return;

        _lastAction = Action.DEL_CHAR;
        _lastDelAction = Action.DEL_CHAR;

        _textToInsert = s.subSequence(start, start + count);
        if (_textToInsert.toString().equals(" ") || _textToInsert.toString().equals("\n"))
            return;

        String iconText = Html.toHtml((Spanned) _textToInsert);

        int startKeySeq;
        if ((startKeySeq = iconText.indexOf(HTML_IMG_SRC_TAG)) != -1) {
            _textToInsert = iconText.subSequence(startKeySeq + HTML_IMG_SRC_TAG.length(), iconText.lastIndexOf("\">"));
            _lastAction = Action.DEL_IMG;
            _lastDelAction = Action.DEL_IMG;
        } else if ((startKeySeq = iconText.indexOf(HTML_COLOR_END_TAG)) != -1) {
            _lastAction = Action.DEL_LINK;

            _textToInsert = "";
        } else {
            _textToInsert = "";
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!_userClick) {
            _userClick = true;
            return;
        }

        if (((_lastAction == Action.DEL_CHAR) &&
                (_textToInsert.toString().equals(" ") || _textToInsert.toString().equals("\n"))))    //if space or
                // newLine was deleted - continue
            return;

        if ((_colorSpansEffacted != null && _colorSpansEffacted.length == 0) ||
                (_linkSpansEffacted != null && _linkSpansEffacted.length == 0)) {
            if (((s.length() < 2) && (_lastDelAction != Action.DEL_IMG)) ||        //2 because need on char and one
            // space to check word
                    (_lastAction == Action.DEL_CHAR)) //if char was deleted and no spans was effected - nothing to do
                return;
        }

        if (_lastAction == Action.KEY_PRESSED)        //check for word
        {
            //get last char that was input
            char endText[] = new char[1];
            s.getChars(_lastChangeInx, _lastChangeInx + 1, endText, 0);

            if ((Character.isLetterOrDigit(endText[0])
                    || _lastDelAction == Action.DEL_IMG)) {
                _lastDelAction = Action.DEL_CHAR;                    //init
                return;
            }
        }

        if (_lastChangeInx > 1) {
            char tmp[] = new char[1];
            s.getChars(_lastChangeInx - 1, _lastChangeInx, tmp, 0);
            if (!Character.isLetterOrDigit(tmp[0]) && (_lastAction == Action.KEY_PRESSED)) {
                int j;
                for (j = 0; j < IconsDataProvider.iconSmileStartEnd.length; j++)        //for smiles
                    if (tmp[0] == IconsDataProvider.iconSmileStartEnd[j])
                        break;
                if (j == IconsDataProvider.iconSmileStartEnd.length)
                    return;
            }
        }

        CharSequence currLineStrat;
        int endOfStartLine = _lastChangeInx;
        if (_lastAction == Action.DEL_LINK || _lastAction == Action.PASTE || _lastAction == Action.DEL_CHAR) {
            if (_colorSpansEffacted != null) {
                for (int i = 0; i < _colorSpansEffacted.length; i++) {
                    int tmp = s.getSpanStart(_colorSpansEffacted[i]);
                    s.removeSpan(_colorSpansEffacted[i]);                //MUST be before doing subSeq (of
                    // currLineStrat and of currLineEnd)
                    if (tmp < endOfStartLine)
                        endOfStartLine = tmp;
                }
                _colorSpansEffacted = null;
            }
        }

        //delete Logo links before removing the image
        if ((_lastAction == Action.DEL_IMG) && (_linkSpansEffacted != null)) {
            for (int i = 0; i < _linkSpansEffacted.length; i++) {
                int tmp = s.getSpanStart(_linkSpansEffacted[i]);
                s.removeSpan(_linkSpansEffacted[i]);
                if (tmp < endOfStartLine)
                    endOfStartLine = tmp;
            }
            _linkSpansEffacted = null;
        }

        if (endOfStartLine < 0)
            endOfStartLine = 0;
        else if (endOfStartLine > s.length())
            endOfStartLine = s.length();

        currLineStrat = s.subSequence(0, endOfStartLine);    //without the last space
        CharSequence currLineEnd = s.subSequence(_lastChangeInx, s.length());

        int lastSpace = 0;
        int fixCursorOffset;
        Spanned currWord;
        CharSequence cs;
        if (_lastAction == Action.KEY_PRESSED)    //search for an image
        {
            lastSpace = currLineStrat.toString().lastIndexOf(' ') + 1;
            currWord = (Spanned) currLineStrat.subSequence(lastSpace, currLineStrat.length());
            Object[] tmpAllSpans = currWord.getSpans(0, currWord.length(), Object.class);
            for (int i = 0; i < tmpAllSpans.length; i++) {
                if ((tmpAllSpans[i].getClass().equals(ClickableSpan.class)) ||
                        (tmpAllSpans[i].getClass().equals(ImageSpan.class)) ||
                        (tmpAllSpans[i].getClass().equals(ForegroundColorSpan.class)))
                    return;
            }

            currLineStrat = currLineStrat.subSequence(0, lastSpace);
            cs = addImageToCharSeq(currWord.toString());
            if (cs == null)
                cs = "";
            fixCursorOffset = cs.length() + 1;
        } else /*if (_lastAction == Action.DEL_IMG) || (_lastAction == Action.PASTE)*/ {
            cs = _textToInsert;
            fixCursorOffset = cs.length();
            _textToInsert = "";
        }

        fixCursorOffset += currLineStrat.length() + _pasteLen;
        currLineStrat = TextUtils.concat(currLineStrat, cs, currLineEnd);

        setText(new SpannedString(currLineStrat), fixCursorOffset);
    }

    private void setSelectedTextColor(int color) {
        int start = Math.min(_etMessage.getSelectionStart(), _etMessage.getSelectionEnd());
        int end = Math.max(_etMessage.getSelectionStart(), _etMessage.getSelectionEnd());

        //set selection to non linked-marked text
        Editable tmp = _etMessage.getText();
        ImageSpan[] imgToCheck = tmp.getSpans(start, end, ImageSpan.class);
        ForegroundColorSpan[] spansToCheck = tmp.getSpans(start, end, ForegroundColorSpan.class);
        if (spansToCheck.length > 0) {
            //get the max spanned end place

            start = tmp.getSpanEnd(spansToCheck[0]);
            int maxEnd;
            for (int i = 1; i < spansToCheck.length; i++) {
                maxEnd = tmp.getSpanEnd(spansToCheck[i]);
                if (maxEnd > start)
                    start = maxEnd;
            }
        }

        if (imgToCheck.length > 0) {
            //get the max spanned end place

            int maxEnd;
            for (int i = 0; i < imgToCheck.length; i++) {
                maxEnd = tmp.getSpanEnd(imgToCheck[i]);
                if (maxEnd > start)
                    start = maxEnd;
            }
        }

        if (start >= end)    //nothing to select (already selected as link)
        {
            _userClick = false;
            _etMessage.setSelection(start);
            return;
        }

        CharSequence selectedKeySeq = tmp.subSequence(start, end);
        if (selectedKeySeq.length() == 0 || selectedKeySeq.toString().equals(" ")) {
            _userClick = false;
            _etMessage.setSelection(end);
            return;
        }

        SpannableStringBuilder coloredKeySeq = new SpannableStringBuilder();
        coloredKeySeq.append(selectedKeySeq);
        coloredKeySeq.setSpan(new ForegroundColorSpan(color), 0, end - start, Spanned.SPAN_INTERMEDIATE);

        SpannedString res = new SpannedString(TextUtils.concat(tmp.subSequence(0, start),
                coloredKeySeq,
                tmp.subSequence(end, _etMessage.length())));
        setText(res, end);
    }

    public void setText(SpannedString text, int cursorPos) {
        _userClick = false;
        _etMessage.removeTextChangedListener(this);

        if ((text == null) || (text.toString().equals(""))) {
            _etMessage.getText().clearSpans();
            _etMessage.setText("");
            _etMessage.setSelection(0);
        } else {
            _etMessage.setText(text, BufferType.SPANNABLE);
            _etMessage.setSelection(cursorPos);
        }

        _etMessage.addTextChangedListener(this);
        _userClick = true;

    }

    public void appendText(SpannedString text) {
        _etMessage.removeTextChangedListener(this);

        char addWithListenner = text.charAt(text.length() - 1);
        boolean endAdding = false;
        if (addWithListenner == ' ' || addWithListenner == '\n' || addWithListenner == '\0') {
            _etMessage.append(text, 0, text.length() - 1);
            endAdding = true;
        } else {
            _etMessage.append(text);
        }

        _etMessage.addTextChangedListener(this);

        if (endAdding) {
            _userClick = true;
            String str = String.format("%c", addWithListenner);
            _etMessage.append(str);
        }

    }

    public String getText(boolean showSymbols) {
        return TextImage.toText(_etMessage.getText(), showSymbols);
    }

    /*
     *
     * parse from HTML to text wrapper
     *
     * to get text from controller (UI component) that contains text (chars) and images
     *
     * params:
     * 	fromControl - the input text
     * 	toPlainText - if to add open/close link symbols
     * 	toSend		- if the text is for sending text - replace html tags to symbols,
     * 	else - replace symbols to html tags
     */
    public static String toText(String fromControl, boolean showSymbols) {
        Editable tmp = new SpannableStringBuilder(fromControl);
        return toText(tmp, showSymbols);
    }

    /*
     *
     * parse from HTML to text
     *
     * to get text from controller (UI component) that contains text (chars) and images
     *
     * params:
     * 	fromControl - the input text
     * 	toPlainText - if to add open/close link symbols
     * 	toSend		- if the text is for sending text - replace html tags to symbols,
     * 	else - replace symbols to html tags
     */
    public static String toText(Editable fromControl, boolean showSymbols) {

        String tmp = Html.toHtml((Spanned) fromControl);
        StringBuilder res = new StringBuilder(tmp);
        int i, endText;

        while ((i = res.indexOf(HTML_IMG_SRC_TAG)) >= 0) {
            endText = res.indexOf("\">", i);
            tmp = res.substring(i + HTML_IMG_SRC_TAG.length(), endText);
            res.replace(i, endText + 2, tmp);                            //endText+2 is the Tag end ( == "\">".length())
        }

        res = changeLinkstate(res, !showSymbols, showSymbols);

        tmp = Html.fromHtml(res.toString()).toString();
        return tmp.substring(0, tmp.length() - 2);

    }

    /*
     * params:
     * 	fromControl - the input text
     * 	removeTags	- if to add open/close link symbols
     * 	toSymbols	- true - change to symbols, false - change to html tags
     */
    public static StringBuilder changeLinkstate(StringBuilder input, boolean removeTags, boolean toSymbols) {
        int i;
        int toSearch, toAdd;

        if (toSymbols) {
            toSearch = 0;
            toAdd = 1;
        } else {
            toSearch = 1;
            toAdd = 0;
        }

        for (int j = 1; j < HTML_COLOR_TAG.length; j++) {
            while ((i = input.indexOf(HTML_COLOR_TAG[j][toSearch])) >= 0) {
                input.replace(i, i + HTML_COLOR_TAG[j][toSearch].length(),
                        (removeTags ? "" : HTML_COLOR_TAG[j][toAdd]));
                i = input.indexOf(HTML_COLOR_TAG[0][toSearch], i);
                input.replace(i, i + HTML_COLOR_TAG[0][toSearch].length(),
                        (removeTags ? "" : HTML_COLOR_TAG[0][toAdd]));
            }
        }

        return input;
    }

    public CharSequence toVisualTextFast(String text) {
        SpannableStringBuilder res = new SpannableStringBuilder();
        int startLink = 0;
        int endLink = 0;
        int i;
        int findEndTag = 0;
        int textOffsetFix = 0;
        int resLinkOffsetFix = 0;

        String[] words = text.split("\\s");
        boolean searchForEndTag = false;
        boolean searchForLinks = text.contains(HTML_COLOR_TAG[0][1]);

        for (i = 0; i < words.length; i++) {
            if (searchForLinks) {
                if (!searchForEndTag &&
                        ((startLink = words[i].indexOf('{')) != -1)) {
                    if (startLink == words[i].length())
                        continue;
                    if (words[i].charAt(startLink + 1) == '[')
                        findEndTag = 1;
                    else if (words[i].charAt(startLink + 1) == ']')
                        findEndTag = 2;
                    else if (words[i].charAt(startLink + 1) == '/')
                        findEndTag = 3;
                    else
                        continue;

                    searchForEndTag = true;
                    startLink += textOffsetFix;
                    resLinkOffsetFix = res.length() + 2;
                }

                if (searchForEndTag) {
                    if ((endLink = words[i].indexOf(HTML_COLOR_TAG[0][1])) != -1) {
                        int offsetToRemoveFromWord = endLink + 2;
                        final String linkToAdd = text.substring(startLink + 2, endLink + textOffsetFix);    //to get
                        // only the word without the symbols

                        endLink += res.length();
                        if (offsetToRemoveFromWord < words[i].length()) {
                            textOffsetFix += offsetToRemoveFromWord;
                            res.append(words[i].substring(0, offsetToRemoveFromWord));
                            words[i] = words[i].substring(offsetToRemoveFromWord);

                            --i;
                        } else {
                            res.append(words[i]);
                            res.append(" ");
                        }


                        if (findEndTag == 1) {
                            ClickableSpan csGoogleLink = new ClickableSpan() {

                                @Override
                                public void onClick(View widget) {
                                    showLinksBubble(linkToAdd, null);
                                }
                            };

                            res.setSpan(csGoogleLink, resLinkOffsetFix, endLink, 0);
                            res.setSpan(new ForegroundColorSpan(GOOGLE_YT_COLOR), resLinkOffsetFix, endLink,
                                    Spanned.SPAN_INTERMEDIATE);
                        } else if (findEndTag == 2) {
                            ClickableSpan csWazeLink = new ClickableSpan() {

                                @Override
                                public void onClick(View widget) {
                                    showLinksBubble(null, linkToAdd);
                                }
                            };

                            res.setSpan(csWazeLink, resLinkOffsetFix, endLink, 0);
                            res.setSpan(new ForegroundColorSpan(WAZE_COLOR), resLinkOffsetFix, endLink,
                                    Spanned.SPAN_INTERMEDIATE);
                        }

                        res.replace(endLink, endLink + 2, "");
                        res.replace(resLinkOffsetFix - 2, resLinkOffsetFix, "");
                        searchForEndTag = false;
                        continue;
                    } else {
                        if (findEndTag == 3) {
                            return getMoreText(words[i]);
                        }
                        res.append(words[i]);
                    }
                }
            }

            if (!searchForEndTag) {
                CharSequence tmp = addImageToCharSeq(words[i]);
                if (tmp == null)
                    res.append(words[i]);
                else
                    res.append(tmp);
            }

            res.append(" ");
            textOffsetFix += words[i].length() + 1; //+1 for the space added between words
        }

        return res;
    }

    public CharSequence toVisualText(String text) {
        Editable res = new SpannableStringBuilder();
        int startWord = 0;
        int startLink = 0;
        char whitesape;
        int i;
        int findEndTag = 0;
        boolean searchForLink = false;

        if (text.contains(HTML_COLOR_TAG[0][1]))
            searchForLink = true;

        for (i = 0; i < text.length(); i++) {
            if ((searchForLink) &&
                    ((text.charAt(i) == '{') || (findEndTag > 0)) &&
                    (i < (text.length() - 1))) {
                if ((findEndTag == 0) &&
                        (text.charAt(i + 1) == '['))    //if google/YT link
                {
                    findEndTag = 1;
                    startLink = i;
                } else if ((findEndTag == 0) &&
                        (text.charAt(i + 1) == ']'))    //if waze link
                {
                    findEndTag = 2;
                    startLink = i;
                } else if ((findEndTag > 0) &&
                        (text.charAt(i) == ']') &&
                        (text.charAt(i + 1) == '}')) {
                    int startSpan = res.length();
                    final String linkToAdd = text.substring(startLink + 2, i);    //to get only the word without the
                    // symbols
                    res.append(linkToAdd);

                    if (findEndTag == 1) {
                        ClickableSpan csGoogleLink = new ClickableSpan() {

                            @Override
                            public void onClick(View widget) {
                                showLinksBubble(linkToAdd, null);
                            }
                        };
                        res.setSpan(csGoogleLink, startSpan, res.length(), 0);
                        res.setSpan(new ForegroundColorSpan(GOOGLE_YT_COLOR), startSpan, res.length(),
                                Spanned.SPAN_INTERMEDIATE);
                    } else if (findEndTag == 2) {
                        ClickableSpan csWazeLink = new ClickableSpan() {

                            @Override
                            public void onClick(View widget) {
                                showLinksBubble(null, linkToAdd);

                            }
                        };
                        res.setSpan(csWazeLink, startSpan, res.length(), 0);
                        res.setSpan(new ForegroundColorSpan(WAZE_COLOR), startSpan, res.length(),
                                Spanned.SPAN_INTERMEDIATE);
                    }

                    findEndTag = 0;
                    ++i;
                    startWord = i + 1;
                }

                continue;    //if Start Tag was found, don't spot until End Tag
            }

            //check for images
            if ((whitesape = text.charAt(i)) == ' ' || (whitesape = text.charAt(i)) == '\n' || i == (text.length() -
                    1)) {
                if (i == (text.length() - 1)) {    //if there is no space in the end of the text
                    whitesape = '\0';
                    ++i;
                }
                String word = text.substring(startWord, i);
                if (word.equals(" ")) {
                    res.append(" ");
                    continue;
                }
                CharSequence tmp = addImageToCharSeq(word);
                if (tmp == null)
                    res.append(word);
                else
                    res.append(tmp);

                res.append(whitesape);
                startWord = i + 1;
            }
        }

        return res;
    }

    private CharSequence addImageToCharSeq(String imageName) {

        SpannableStringBuilder res = new SpannableStringBuilder();
        LogoData wantedLogo;
        IconData wantedIcon;

        String tmpName = imageName.toLowerCase(Locale.US);
        int found = 0;
        int tmpImageId = 0;
        float tmpHiegth = 0;
        float tmpWiegth = 0;
        char firstChar = '\0';
        char chkFirstChar;

        if (imageName == null || imageName.length() < 1)
            return imageName;

        chkFirstChar = tmpName.charAt(0);

        int i = tmpName.length();
        if (i > 2) {
            for (; i > 0; i--) {
                if (Character.isLetterOrDigit(tmpName.charAt(i - 1))) {
                    break;
                }
            }
        }
        String endImageName = tmpName.substring(i);
        tmpName = tmpName.substring(0, i);

        wantedLogo = LogoDataProvider.htLogos.get(tmpName);
        if (wantedLogo == null && tmpName.length() > 1 &&
                checkHebFirstChar(chkFirstChar))    //start chars in Hebrew
        {
            //in hebrew - check without first char
            wantedLogo = LogoDataProvider.htLogos.get(tmpName.substring(1));
            if (wantedLogo != null)
                firstChar = chkFirstChar;
        }

        if (wantedLogo == null)
            found++;
        else {
            if (!Settings.showLogos)
                return imageName;

            tmpImageId = wantedLogo.image;
            tmpName = ((firstChar == '\0') ? imageName.toLowerCase() : imageName.toLowerCase().substring(1));
            tmpHiegth = wantedLogo.hiegth;
            tmpWiegth = wantedLogo.wiegth;
        }

        if (found == 1)        //if not Logo - search for icon
        {
            //search the word if in icon list
            if (Settings.isEnglish) {
                //if in English - search first in the English DB
                wantedIcon = IconsDataProvider.htIcons.get(tmpName);
                if (wantedIcon == null) {
                    wantedIcon = IconsDataProvider.htIconsHeb.get(tmpName);
                    if (wantedIcon == null && tmpName.length() > 1 &&
                            checkHebFirstChar(chkFirstChar))    //start chars in Hebrew
                    {
                        //in hebrew - check without first char
                        wantedIcon = IconsDataProvider.htIconsHeb.get(tmpName.substring(1));
                        if (wantedIcon != null)
                            firstChar = chkFirstChar;
                    }
                }
            } else {
                //if in Hebrew - search first in the Hebrew DB
                wantedIcon = IconsDataProvider.htIconsHeb.get(tmpName);
                if (wantedIcon == null && tmpName.length() > 1 &&
                        checkHebFirstChar(chkFirstChar))    //start chars in Hebrew
                {
                    //in Hebrew - check without first char
                    wantedIcon = IconsDataProvider.htIconsHeb.get(tmpName.substring(1));
                    if (wantedIcon != null)
                        firstChar = chkFirstChar;
                }

                if (wantedIcon == null) {
                    //if not found in Hebrew with/without first char
                    wantedIcon = IconsDataProvider.htIcons.get(tmpName);
                }
            }

            if (wantedIcon == null)
                found++;
            else {
                if (!Settings.showIcons)
                    return imageName;

                tmpImageId = wantedIcon.index;
                tmpName = wantedIcon.name;
                tmpHiegth = wantedIcon.hiegth;
                tmpWiegth = wantedIcon.wiegth;
            }
        }

        if (found >= 2)
            return imageName;

        final int imageId = tmpImageId;
        final float hiegth = tmpHiegth;
        final float wiegth = tmpWiegth;

        ImageGetter imageGetter = new ImageGetter() {
            public Drawable getDrawable(String source) {
                Drawable d = _context.getResources().getDrawable(imageId);// wantedIcon.imageDraw;
                d.setBounds(0, 0, (int) (d.getIntrinsicWidth() / wiegth), (int) (d.getIntrinsicHeight() / hiegth));
                //wantedIcon.weight, wantedIcon.height);//d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };
        CharSequence tmpImgSpan = Html.fromHtml(HTML_IMG_SRC_TAG + tmpName, imageGetter, null);
        if (firstChar != '\0')
            res.append(firstChar);
        res.append(tmpImgSpan);

        if (wantedLogo != null) {
            final String googleLink = tmpName;
            final String wazeLink = wantedLogo.waseUrl;
            ClickableSpan csLink = new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    showLinksBubble(googleLink, wazeLink);
                }
            };
            res.setSpan(csLink, 0, res.length(), Spanned.SPAN_INTERMEDIATE);
        }

        res.append(endImageName);

        return res;
    }

    /*
     * Check char if one of the hebrew addon char to word
     */
    private boolean checkHebFirstChar(char chkFirstChar) {
        return (chkFirstChar == '�' || chkFirstChar == '�' || chkFirstChar == '�' || chkFirstChar == '�' ||
                chkFirstChar == '�');
    }

    @Override
    public boolean onLongClick(View arg0) {

        if (!Settings.showIcons)
            return true;

        if (_etMessage.getSelectionStart() != _etMessage.getSelectionEnd())        //text is already selected
            return true;

        StringBuilder text = new StringBuilder(_etMessage.getText());

        if (text.length() == 0)
            return false;

        _onLongClickState = true;
        _etMessage.removeTextChangedListener(this);

        int selectStart = _etMessage.getSelectionStart();
        int selectEnd = _etMessage.getSelectionEnd();

        if (_etMessage.getSelectionStart() == text.length())    //the cursor is in the end of text
        {
            --selectStart;
            while (text.charAt(selectStart) == ' ')
                --selectStart;

            selectStart = text.substring(0, selectStart).lastIndexOf(' ') + 1;
        } else            //the cursor is in the middle
        {
            selectEnd = text.indexOf(" ", selectEnd);
            if (selectEnd == -1)
                selectEnd = text.length();
            selectStart = text.substring(0, selectStart).lastIndexOf(' ') + 1;
        }

        Object[] selectedSpans = _etMessage.getText().getSpans(selectStart, selectEnd, Object.class);
        for (int i = 0; i < selectedSpans.length; i++) {
            if (selectedSpans[i].getClass().equals(Selection.class))
                _etMessage.getText().removeSpan(selectedSpans[i]);
        }


        _userClick = false;
        _etMessage.setSelection(selectStart, selectEnd);

        final int finSelectEnd = selectEnd;
        final Dialog dialog = new Dialog(_context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.words_selection_dialog);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                _etMessage.setSelection(finSelectEnd);
            }
        });

        final LinearLayout llSelectBack = (LinearLayout) dialog.findViewById(R.id.llSelectWord);
        final ImageView btnSelectBack = (ImageView) dialog.findViewById(R.id.btnSelectBack);

        LinearLayout llSetWaze = (LinearLayout) dialog.findViewById(R.id.llWaze);
        LinearLayout llSetGoogle = (LinearLayout) dialog.findViewById(R.id.llGoogle);
        LinearLayout llSetYoutube = (LinearLayout) dialog.findViewById(R.id.llYoutube);

        if (selectStart == 0)
            btnSelectBack.setEnabled(false);

        llSelectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnSelectBack.isEnabled())
                    return;

                int selectStart = _etMessage.getSelectionStart() - 1;
                while (selectStart > 0 && (_etMessage.getText().charAt(selectStart) == ' ' || _etMessage.getText().charAt(selectStart) == '\n'))
                    --selectStart;

                String startLine = _etMessage.getText().toString().substring(0, selectStart);
                selectStart = startLine.lastIndexOf(' ') + 1;

                if (selectStart == 0)
                    btnSelectBack.setEnabled(false);

                int selectEnd = _etMessage.getSelectionEnd();
                _userClick = false;
                _etMessage.setSelection(selectStart, selectEnd);
            }
        });

        llSetWaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTextColor(WAZE_COLOR);
                dialog.dismiss();
            }
        });

        llSetGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTextColor(GOOGLE_YT_COLOR);
                dialog.dismiss();
            }
        });

        llSetYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTextColor(GOOGLE_YT_COLOR);
                dialog.dismiss();
            }
        });

        dialog.show();

        _etMessage.addTextChangedListener(this);

        return true;
    }

    private void showLinksBubble(final String search, final String address) {
        final Dialog dialog = new Dialog(_context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.links_bubble_dialog);

        LinearLayout llWaze = (LinearLayout) dialog.findViewById(R.id.llWaze);
        ImageView sepWaze = (ImageView) dialog.findViewById(R.id.sepWaze);

        LinearLayout llGoogle = (LinearLayout) dialog.findViewById(R.id.llGoogle);
        ImageView sepGoogle = (ImageView) dialog.findViewById(R.id.sepGoogle);

        LinearLayout llYoutube = (LinearLayout) dialog.findViewById(R.id.llYoutube);

        //View relevant options
        if (address == null || address.equals("")) {
            llWaze.setVisibility(View.GONE);
            sepWaze.setVisibility(View.GONE);
        }
        if (search == null || search.equals("")) {
            llGoogle.setVisibility(View.GONE);
            sepGoogle.setVisibility(View.GONE);
            llYoutube.setVisibility(View.GONE);

            sepWaze.setVisibility(View.GONE);
        }

        llWaze.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog != null)
                    dialog.dismiss();

                openWaze(address);
            }
        });

        llGoogle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog != null)
                    dialog.dismiss();

                openGoogle(search);
            }
        });

        llYoutube.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog != null)
                    dialog.dismiss();

                openYoutube(search);
            }
        });

        dialog.show();
    }

    private CharSequence getMoreText(String word) {
        StringBuilder res = new StringBuilder(100);
        boolean toContinue = true;

        if (!word.contains("" + (char) 66) && word.contains(YT_SEARCH.substring(11, 12)))
            return word;

        res.append((char) 68);
        String tmp = String.format(Locale.US, " %C%c%c%C ", ERROR_OPEN_GOOGLE.charAt(20),
                REGISTER_LINK_URL.charAt(8), YT_SEARCH.charAt(23), 97);
        res.append(tmp);
        res.insert(1, (char) 118);

        if (tmp == null) {
            return "";
        }

        toContinue = true;
        if (toContinue) {
            res.insert(5, HTML_IMG_SRC_TAG.charAt(1));
            tmp = String.format(Locale.US, "%C%C%C",
                    HTML_COLOR_END_TAG.charAt(4),
                    HTML_IMG_SRC_TAG.charAt(2), 122);
            res.insert(2, (char) 58);
            res.insert(1, GOOGLE_SEARCH_URL.charAt(12));
            res.append(tmp);
        }

        for (int i = 12; i < 15; i += 2)
            res.insert(i, ERROR_OPEN_YT.charAt(20));

        res.insert(3, GOOGLE_SEARCH_URL.charAt(18));

        return res;
    }

    private void openWaze(String location) {
        boolean isWazeFound = true;

        try {
            String directionweburl = "http://maps.google.com/maps?q=" + URLEncoder.encode(location, "utf-8");
            ;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(directionweburl));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

            if (_context != null)
                _context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            isWazeFound = false;
            Toast.makeText(_context, "trying to open google play", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(_context, ERROR_OPEN_WAZE, Toast.LENGTH_SHORT).show();
        }

        try {
            if (!isWazeFound) {
                isWazeFound = true;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
                _context.startActivity(intent);
            }
        } catch (ActivityNotFoundException ex) {
            isWazeFound = false;
            Toast.makeText(_context, "google play not found", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(_context, "can't open goole play", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGoogle(String term) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            int index = LogoDataProvider.findItemIndex(term);
            term = URLEncoder.encode(term, "utf-8");
            String url = String.format(Locale.US, "%s%s", GOOGLE_SEARCH_URL, term);

            if (index > -1)    //if logo found
            {
                url = String.format(Locale.US, "%s%d&name=%s&to=%s", REGISTER_LINK_URL, index, term, url);
            }

            intent.setData(Uri.parse(url));

            if (_context != null)
                _context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(_context, ERROR_OPEN_GOOGLE, Toast.LENGTH_SHORT).show();
        }
    }

    private void openYoutube(String term) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            int index = LogoDataProvider.findItemIndex(term);
            term = URLEncoder.encode(term, "utf-8");
            String url = String.format(Locale.US, "%s%s", YT_SEARCH, term);

            if (index > -1)    //if logo found
            {
                url = String.format(Locale.US, "%s%d&name=%s&to=%s", REGISTER_LINK_URL, index, term, url);
            }

            intent.setData(Uri.parse(url));

            if (_context != null)
                _context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(_context, ERROR_OPEN_YT, Toast.LENGTH_SHORT).show();
        }
    }
}

