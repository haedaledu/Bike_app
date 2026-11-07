package com.haedaledu.bikeapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends ComponentActivity {
    private static final int GREEN = Color.rgb(24, 113, 83);
    private static final int TEXT = Color.rgb(27, 48, 40);
    private static final int MUTED = Color.rgb(100, 120, 110);
    private static final int BORDER = Color.rgb(224, 234, 227);
    private static final int BACKGROUND = Color.rgb(245, 248, 244);
    private static final int MINT = Color.rgb(229, 243, 232);
    private static final int BLUE = Color.rgb(54, 103, 144);
    private static final int PALE_BLUE = Color.rgb(232, 242, 249);
    private static final int AMBER = Color.rgb(140, 91, 31);
    private static final int PALE_AMBER = Color.rgb(251, 242, 225);
    private static final int WHITE = Color.WHITE;
    private static final String PREFERENCES = "bike-maintenance";

    private static final List<CareItem> MAINTENANCE_ITEMS = Arrays.asList(
            new CareItem("체인 청소 및 오일 도포",
                    "오염 물질을 닦아내고 자전거 전용 오일을 바른 뒤, 체인 겉면에 남은 기름을 닦아냅니다."),
            new CareItem("타이어 공기압 점검",
                    "타이어에 표시된 권장 공기압(PSI/BAR)을 확인하고 마모나 갈라짐이 없는지 살핍니다."),
            new CareItem("브레이크 패드 및 장력 점검",
                    "패드 마모 상태와 브레이크 레버 감도를 확인하고 브레이크가 정상 작동하는지 점검합니다."),
            new CareItem("변속기 세팅 점검",
                    "변속할 때 체인이 튀거나 소음이 나지 않는지 확인하고 변속 상태를 살핍니다."),
            new CareItem("구동계 이물질 제거",
                    "체인링과 스프라켓 톱니 사이에 낀 흙이나 이물질을 제거합니다."),
            new CareItem("볼트 결합 상태 점검",
                    "핸들바, 스템, 안장, 페달 등 주요 부위의 볼트가 풀리지 않았는지 확인합니다."),
            new CareItem("프레임 닦기 및 균열 점검",
                    "차체 표면을 닦고 프레임에 균열이나 손상이 없는지 살핍니다."),
            new CareItem("휠 정렬 점검",
                    "휠이 좌우로 흔들리거나 정렬이 틀어지지 않았는지 림과 스포크 상태를 확인합니다.")
    );

    private static final List<Situation> SITUATIONS = Arrays.asList(
            new Situation("rain", "자전거가 비에 젖었어요", Arrays.asList(
                    new CareItem("차체 및 구동계 물기 제거",
                            "마른 타월이나 천으로 프레임, 안장, 체인과 톱니의 빗물 및 오염물을 닦아냅니다."),
                    new CareItem("체인 완전 건조 및 녹 방지",
                            "체인과 구동계 사이에 남은 물기를 닦고 충분히 말려 부식을 막습니다."),
                    new CareItem("체인 오일 재도포",
                            "체인 마디마다 자전거 전용 오일을 바르고 겉면에 남은 기름은 가볍게 닦아냅니다."),
                    new CareItem("베어링 부위 건조 및 점검",
                            "헤드셋, 크랭크 축, 휠 허브 주변에 물기가 고이지 않았는지 확인하고 충분히 말립니다.")
            )),
            new Situation("before-ride", "라이딩 전이에요", Arrays.asList(
                    new CareItem("타이어 공기압 및 표면 점검",
                            "권장 공기압을 확인하고 타이어 표면에 마모, 찢어짐, 이물질이 없는지 살핍니다."),
                    new CareItem("브레이크 제동력 확인",
                            "앞뒤 브레이크 레버를 잡아보고 자전거를 살짝 밀어 제동이 잘 되는지 확인합니다."),
                    new CareItem("구동계 및 체인 점검",
                            "체인이 지나치게 건조하거나 소음이 나지 않는지, 변속이 잘 되는지 확인합니다."),
                    new CareItem("바퀴·핸들·안장 고정 확인",
                            "바퀴 고정 장치와 핸들바, 안장이 흔들리지 않도록 단단히 고정됐는지 확인합니다.")
            )),
            new Situation("fall", "자전거가 넘어졌어요", Arrays.asList(
                    new CareItem("변속기 행어 휘어짐 확인",
                            "변속기가 휠 쪽으로 휘거나 스포크에 닿지 않았는지 외관을 살핍니다."),
                    new CareItem("변속 및 체인 간섭 점검",
                            "변속기를 움직일 때 체인이나 변속기가 바퀴 살에 걸리지 않는지 확인합니다."),
                    new CareItem("핸들바 및 안장 정렬 점검",
                            "핸들바와 안장이 앞바퀴 및 프레임 방향과 바르게 정렬되어 있는지 확인합니다."),
                    new CareItem("브레이크 및 프레임 손상 검사",
                            "브레이크가 정상 작동하는지, 프레임과 부품 표면에 균열이나 파손이 없는지 살핍니다.")
            ))
    );

    private final Deque<Screen> screenStack = new ArrayDeque<>();
    private final Calendar selectedDate = Calendar.getInstance();
    private String selectedSituationId;
    private LinearLayout content;
    private TextView progressText;
    private ProgressBar progressBar;
    private Screen currentScreen = Screen.HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BACKGROUND);
        getWindow().setNavigationBarColor(BACKGROUND);
        int systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        }
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);

        if (savedInstanceState == null) {
            screenStack.addLast(Screen.HOME);
        } else {
            selectedDate.setTimeInMillis(savedInstanceState.getLong("selected-date", System.currentTimeMillis()));
            selectedSituationId = savedInstanceState.getString("selected-situation");
            ArrayList<String> savedStack = savedInstanceState.getStringArrayList("screen-stack");
            if (savedStack != null) {
                for (String name : savedStack) {
                    try {
                        screenStack.addLast(Screen.valueOf(name));
                    } catch (IllegalArgumentException ignored) {
                        screenStack.clear();
                        break;
                    }
                }
            }
            if (screenStack.isEmpty()) {
                screenStack.addLast(Screen.HOME);
            }
        }
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
        render();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("selected-date", selectedDate.getTimeInMillis());
        outState.putString("selected-situation", selectedSituationId);
        ArrayList<String> savedStack = new ArrayList<>();
        for (Screen screen : screenStack) {
            savedStack.add(screen.name());
        }
        outState.putStringArrayList("screen-stack", savedStack);
        super.onSaveInstanceState(outState);
    }

    private void navigateBack() {
        if (screenStack.size() > 1) {
            screenStack.removeLast();
            render();
        } else {
            finish();
        }
    }

    private void navigateTo(Screen screen) {
        if (screenStack.peekLast() != screen) {
            screenStack.addLast(screen);
        }
        render();
    }

    private void render() {
        currentScreen = screenStack.peekLast() == null ? Screen.HOME : screenStack.peekLast();
        progressText = null;
        progressBar = null;
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BACKGROUND);
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        LinearLayout toolbar = new LinearLayout(this);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setPadding(dp(20), dp(8), dp(20), dp(8));
        if (currentScreen != Screen.HOME) {
            ImageView back = icon(R.drawable.ic_back, TEXT);
            back.setPadding(dp(12), dp(12), dp(12), dp(12));
            back.setBackground(ripple(WHITE, BORDER, 16));
            back.setContentDescription("뒤로");
            back.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
            LinearLayout.LayoutParams backParams = new LinearLayout.LayoutParams(dp(48), dp(48));
            backParams.rightMargin = dp(12);
            toolbar.addView(back, backParams);
            back.setOnClickListener(view -> navigateBack());
        } else {
            ImageView brand = icon(R.drawable.ic_launcher_foreground, WHITE);
            brand.setBackground(rounded(GREEN, GREEN, 14));
            LinearLayout.LayoutParams brandParams = new LinearLayout.LayoutParams(dp(42), dp(42));
            brandParams.rightMargin = dp(12);
            toolbar.addView(brand, brandParams);
        }
        TextView title = text(currentScreen == Screen.HOME ? "자전거 관리법" : screenTitle(), 17, TEXT, true);
        toolbar.addView(title, new LinearLayout.LayoutParams(0, -2, 1));
        if (currentScreen == Screen.HOME) {
            TextView badge = text("BIKE CARE", 10, GREEN, true);
            badge.setLetterSpacing(0.08f);
            toolbar.addView(badge, new LinearLayout.LayoutParams(-2, -2));
        }
        root.addView(toolbar, new LinearLayout.LayoutParams(-1, -2));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.setVerticalScrollBarEnabled(false);
        FrameLayout contentContainer = new FrameLayout(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(16), dp(20), dp(28));
        int width = Math.min(getResources().getConfiguration().screenWidthDp, 640);
        contentContainer.addView(content,
                new FrameLayout.LayoutParams(dp(width), -2, Gravity.CENTER_HORIZONTAL));
        scroll.addView(contentContainer, new ScrollView.LayoutParams(-1, -2));
        LinearLayout screenContent = content;
        scroll.addOnLayoutChangeListener((view, left, top, right, bottom,
                oldLeft, oldTop, oldRight, oldBottom) -> {
            int availableWidth = Math.min(right - left, dp(640));
            if (screenContent.getLayoutParams().width != availableWidth) {
                screenContent.getLayoutParams().width = availableWidth;
                screenContent.requestLayout();
            }
        });
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));
        setContentView(root);

        switch (currentScreen) {
            case HOME:
                renderHome();
                break;
            case SCHEDULE:
                renderSchedule();
                break;
            case SITUATIONS:
                renderSituations();
                break;
            case GUIDE:
                renderGuide();
                break;
        }
    }

    private String screenTitle() {
        switch (currentScreen) {
            case SCHEDULE:
                return "날짜별 관리 일정";
            case SITUATIONS:
                return "상황별 관리법";
            case GUIDE:
                Situation situation = selectedSituation();
                return situation == null ? "관리 안내" : situation.title;
            default:
                return "자전거 관리법";
        }
    }

    private void renderHome() {
        LinearLayout hero = card();
        hero.setPadding(dp(24), dp(24), dp(24), dp(20));
        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.rgb(20, 77, 59), GREEN});
        gradient.setCornerRadius(dp(24));
        hero.setBackground(gradient);
        TextView eyebrow = text("매일의 작은 점검, 더 즐거운 라이딩", 12, Color.rgb(199, 227, 207), false);
        hero.addView(eyebrow, matchWrap(-2));
        TextView heading = text("오늘도 가볍게,\n안전하게 달려요.", 29, WHITE, true);
        LinearLayout.LayoutParams headingParams = matchWrap(-2);
        headingParams.topMargin = dp(12);
        hero.addView(heading, headingParams);
        ImageView bicycle = icon(R.drawable.ic_launcher_foreground, Color.rgb(203, 234, 186));
        bicycle.setScaleType(ImageView.ScaleType.FIT_CENTER);
        hero.addView(bicycle, matchWrap(108));
        TextView heroNote = text("좋은 라이딩은 작은 관리에서 시작돼요.", 13, WHITE, false);
        hero.addView(heroNote, matchWrap(-2));
        addCardToContent(hero);
        addText("나의 자전거 관리", 20, TEXT, true, 12);
        addText("필요한 관리 방법을 선택해 주세요.", 14, MUTED, false, 4);
        addSpace(16);
        addActionButton("날짜별 관리 일정", "8가지 점검을 기록하고 관리해요",
                R.drawable.ic_calendar, GREEN, MINT, () -> navigateTo(Screen.SCHEDULE));
        addActionButton("상황별 자전거 관리법", "라이딩 전후, 상황에 맞는 관리 가이드",
                R.drawable.ic_shield, BLUE, PALE_BLUE, () -> navigateTo(Screen.SITUATIONS));
        addSpace(6);
        TextView footer = text("내 기기에 안전하게 저장 · 오프라인 사용", 12, MUTED, false);
        footer.setGravity(Gravity.CENTER);
        content.addView(footer, matchWrap(-2));
    }

    private void renderSchedule() {
        addText("한 번의 점검,\n더 안심되는 라이딩.", 27, TEXT, true);
        addText("완료한 항목을 체크해 나만의 관리 기록을 남겨요.", 14, MUTED, false, 8);
        addSpace(20);

        Button dateButton = outlineButton(displayDate());
        dateButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_calendar, 0, 0, 0);
        dateButton.setCompoundDrawablePadding(dp(12));
        dateButton.setPadding(dp(18), dp(12), dp(18), dp(12));
        dateButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        content.addView(dateButton, matchWrap(-2));
        dateButton.setOnClickListener(view -> showDatePicker());

        addSpace(14);
        LinearLayout progressCard = card();
        progressCard.setBackground(rounded(MINT, MINT, 20));
        progressCard.addView(text("차근차근, 오늘의 관리", 13, GREEN, true), matchWrap(-2));
        progressText = text("", 23, TEXT, true);
        LinearLayout.LayoutParams progressParams = matchWrap(-2);
        progressParams.topMargin = dp(8);
        progressCard.addView(progressText, progressParams);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(MAINTENANCE_ITEMS.size());
        progressBar.setProgressTintList(ColorStateList.valueOf(GREEN));
        progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.rgb(201, 223, 206)));
        LinearLayout.LayoutParams barParams = matchWrap(8);
        barParams.topMargin = dp(12);
        progressCard.addView(progressBar, barParams);
        addCardToContent(progressCard);
        addText("관리 체크리스트", 18, TEXT, true, 10);
        addSpace(12);

        String storageKey = PREFERENCES + ":" + dateKey();
        for (int i = 0; i < MAINTENANCE_ITEMS.size(); i++) {
            addChecklistItem(MAINTENANCE_ITEMS.get(i), i, storageKey);
        }
        updateProgress();
        Button resetButton = outlineButton("이 날짜 기록 초기화");
        resetButton.setTextColor(MUTED);
        content.addView(resetButton, matchWrap(-2));
        resetButton.setOnClickListener(view -> confirmReset());
    }

    private void renderSituations() {
        addText("지금, 어떤 상황인가요?", 26, TEXT, true);
        addText("상황에 꼭 맞는 관리 방법을 모았어요.", 14, MUTED, false, 8);
        addSpace(24);
        for (Situation situation : SITUATIONS) {
            addActionButton(situation.title, situationDescription(situation.id),
                    situationIcon(situation.id), situationColor(situation.id),
                    situationBackground(situation.id), () -> {
                selectedSituationId = situation.id;
                navigateTo(Screen.GUIDE);
            });
        }
    }

    private void renderGuide() {
        Situation situation = selectedSituation();
        if (situation == null) {
            screenStack.removeLast();
            render();
            return;
        }
        LinearLayout summary = card();
        summary.setBackground(rounded(situationBackground(situation.id),
                situationBackground(situation.id), 22));
        ImageView symbol = icon(situationIcon(situation.id), situationColor(situation.id));
        summary.addView(symbol, new LinearLayout.LayoutParams(dp(40), dp(40)));
        TextView heading = text(situation.title, 24, TEXT, true);
        LinearLayout.LayoutParams headingParams = matchWrap(-2);
        headingParams.topMargin = dp(14);
        summary.addView(heading, headingParams);
        TextView subtitle = text("4가지 점검으로 꼼꼼하게 관리해요.", 14, MUTED, false);
        LinearLayout.LayoutParams subtitleParams = matchWrap(-2);
        subtitleParams.topMargin = dp(6);
        summary.addView(subtitle, subtitleParams);
        addCardToContent(summary);
        addText("이렇게 관리해 주세요", 18, TEXT, true, 12);
        addSpace(14);
        for (int i = 0; i < situation.items.size(); i++) {
            addGuideItem(situation.items.get(i), i + 1);
        }
        LinearLayout safety = card();
        safety.setBackground(rounded(PALE_AMBER, PALE_AMBER, 18));
        safety.addView(text("안전이 가장 중요해요", 15, AMBER, true), matchWrap(-2));
        TextView warning = text("손상이나 이상이 발견되면 라이딩을 멈추고 정비 전문가에게 점검을 받으세요.",
                14, AMBER, false);
        LinearLayout.LayoutParams warningParams = matchWrap(-2);
        warningParams.topMargin = dp(6);
        safety.addView(warning, warningParams);
        addCardToContent(safety);
    }

    private void addChecklistItem(CareItem item, int index, String storageKey) {
        LinearLayout card = card();
        CheckBox checkbox = new CheckBox(this);
        checkbox.setText(item.title);
        checkbox.setTextColor(TEXT);
        checkbox.setTextSize(16);
        checkbox.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        checkbox.setButtonTintList(new ColorStateList(
                new int[][]{new int[]{android.R.attr.state_checked}, new int[]{}},
                new int[]{GREEN, MUTED}));
        checkbox.setPadding(0, 0, 0, 0);
        checkbox.setMinHeight(dp(48));
        checkbox.setBackground(ripple(Color.TRANSPARENT, Color.TRANSPARENT, 12));

        Set<String> completed = getPreferences(MODE_PRIVATE).getStringSet(storageKey, Collections.emptySet());
        checkbox.setChecked(completed.contains(String.valueOf(index)));
        card.setBackground(rounded(checkbox.isChecked() ? MINT : WHITE,
                checkbox.isChecked() ? MINT : BORDER, 20));
        card.addView(checkbox, matchWrap(-2));
        TextView description = text(item.description, 14, MUTED, false);
        LinearLayout.LayoutParams descriptionParams = matchWrap(-2);
        descriptionParams.leftMargin = dp(34);
        descriptionParams.topMargin = dp(2);
        card.addView(description, descriptionParams);
        addCardToContent(card);

        checkbox.setOnCheckedChangeListener((button, checked) -> {
            Set<String> updated = new HashSet<>(getPreferences(MODE_PRIVATE)
                    .getStringSet(storageKey, Collections.emptySet()));
            if (checked) {
                updated.add(String.valueOf(index));
            } else {
                updated.remove(String.valueOf(index));
            }
            getPreferences(MODE_PRIVATE).edit().putStringSet(storageKey, updated).apply();
            card.setBackground(rounded(checked ? MINT : WHITE, checked ? MINT : BORDER, 20));
            updateProgress();
        });
    }

    private void addGuideItem(CareItem item, int step) {
        LinearLayout card = card();
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        TextView number = text(String.format(Locale.getDefault(), "%02d", step), 13, GREEN, true);
        number.setGravity(Gravity.CENTER);
        number.setBackground(rounded(MINT, MINT, 12));
        LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(dp(36), dp(36));
        numberParams.rightMargin = dp(12);
        row.addView(number, numberParams);
        row.addView(text(item.title, 16, TEXT, true), new LinearLayout.LayoutParams(0, -2, 1));
        card.addView(row, matchWrap(-2));
        TextView description = text(item.description, 15, MUTED, false);
        LinearLayout.LayoutParams descriptionParams = matchWrap(-2);
        descriptionParams.topMargin = dp(12);
        card.addView(description, descriptionParams);
        addCardToContent(card);
    }

    private void addActionButton(String label, String description, int iconResource,
                                 int accent, int iconBackground, Runnable action) {
        LinearLayout button = new LinearLayout(this);
        button.setGravity(Gravity.CENTER_VERTICAL);
        button.setPadding(dp(16), dp(20), dp(16), dp(20));
        button.setMinimumHeight(dp(96));
        button.setBackground(ripple(WHITE, BORDER, 20));
        button.setElevation(dp(1));
        button.setFocusable(true);
        button.setContentDescription(getString(R.string.action_with_description, label, description));
        ViewCompat.setAccessibilityDelegate(button, new androidx.core.view.AccessibilityDelegateCompat() {
            @Override
            public void onInitializeAccessibilityNodeInfo(View host,
                    androidx.core.view.accessibility.AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setClassName(Button.class.getName());
            }
        });
        ImageView symbol = icon(iconResource, accent);
        symbol.setPadding(dp(12), dp(12), dp(12), dp(12));
        symbol.setBackground(rounded(iconBackground, iconBackground, 16));
        button.addView(symbol, new LinearLayout.LayoutParams(dp(48), dp(48)));
        LinearLayout labels = new LinearLayout(this);
        labels.setOrientation(LinearLayout.VERTICAL);
        labels.addView(text(label, 16, TEXT, true), matchWrap(-2));
        TextView detail = text(description, 13, MUTED, false);
        LinearLayout.LayoutParams detailParams = matchWrap(-2);
        detailParams.topMargin = dp(6);
        labels.addView(detail, detailParams);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(0, -2, 1);
        labelParams.leftMargin = dp(14);
        labelParams.rightMargin = dp(8);
        button.addView(labels, labelParams);
        button.addView(icon(R.drawable.ic_chevron, MUTED),
                new LinearLayout.LayoutParams(dp(18), dp(18)));
        for (int i = 0; i < button.getChildCount(); i++) {
            button.getChildAt(i).setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS);
        }
        button.setOnClickListener(view -> action.run());
        LinearLayout.LayoutParams params = matchWrap(-2);
        params.bottomMargin = dp(12);
        content.addView(button, params);
    }

    private Button outlineButton(String label) {
        Button button = new Button(this);
        button.setAllCaps(false);
        button.setText(label);
        button.setTextColor(TEXT);
        button.setTextSize(15);
        button.setGravity(Gravity.CENTER);
        button.setMinHeight(dp(52));
        button.setPadding(dp(16), dp(12), dp(16), dp(12));
        button.setBackground(ripple(WHITE, BORDER, 16));
        return button;
    }

    private LinearLayout card() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(rounded(WHITE, BORDER, 20));
        return card;
    }

    private void addCardToContent(LinearLayout card) {
        LinearLayout.LayoutParams params = matchWrap(-2);
        params.bottomMargin = dp(12);
        content.addView(card, params);
    }

    private void addText(String value, int size, int color, boolean bold) {
        addText(value, size, color, bold, 0);
    }

    private void addText(String value, int size, int color, boolean bold, int topMargin) {
        TextView view = text(value, size, color, bold);
        LinearLayout.LayoutParams params = matchWrap(-2);
        params.topMargin = dp(topMargin);
        content.addView(view, params);
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setIncludeFontPadding(false);
        view.setLineSpacing(dp(3), 1.12f);
        if (bold) {
            view.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        }
        return view;
    }

    private void addSpace(int height) {
        content.addView(new View(this), new LinearLayout.LayoutParams(1, dp(height)));
    }

    private LinearLayout.LayoutParams matchWrap(int height) {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height < 0 ? height : dp(height));
    }

    private GradientDrawable rounded(int fillColor, int strokeColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        drawable.setCornerRadius(dp(radius));
        if (strokeColor != fillColor) {
            drawable.setStroke(dp(1), strokeColor);
        }
        return drawable;
    }

    private RippleDrawable ripple(int fillColor, int strokeColor, int radius) {
        return new RippleDrawable(ColorStateList.valueOf(Color.argb(25, 24, 113, 83)),
                rounded(fillColor, strokeColor, radius), rounded(WHITE, WHITE, radius));
    }

    private ImageView icon(int resource, int tint) {
        ImageView image = new ImageView(this);
        image.setImageResource(resource);
        image.setImageTintList(ColorStateList.valueOf(tint));
        image.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        return image;
    }

    private int situationIcon(String id) {
        return "rain".equals(id) ? R.drawable.ic_rain
                : "fall".equals(id) ? R.drawable.ic_shield : R.drawable.ic_sun;
    }

    private int situationColor(String id) {
        return "rain".equals(id) ? BLUE : "fall".equals(id) ? AMBER : GREEN;
    }

    private int situationBackground(String id) {
        return "rain".equals(id) ? PALE_BLUE : "fall".equals(id) ? PALE_AMBER : MINT;
    }

    private String situationDescription(String id) {
        return "rain".equals(id) ? "물기 제거부터 체인 관리까지"
                : "fall".equals(id) ? "충격을 받은 부위를 꼼꼼하게 점검해요" : "출발 전, 안전을 위한 4가지 점검";
    }

    private int dp(float value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private String dateKey() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(selectedDate.getTime());
    }

    private String displayDate() {
        return new SimpleDateFormat("yyyy년 M월 d일 (E)", Locale.KOREAN).format(selectedDate.getTime());
    }

    private void showDatePicker() {
        new DatePickerDialog(
                this,
                (picker, year, month, day) -> {
                    selectedDate.set(year, month, day);
                    render();
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void confirmReset() {
        String key = PREFERENCES + ":" + dateKey();
        Set<String> completed = getPreferences(MODE_PRIVATE).getStringSet(key, Collections.emptySet());
        if (completed.isEmpty()) {
            Toast.makeText(this, "초기화할 관리 기록이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("관리 기록 초기화")
                .setMessage(displayDate() + "의 완료 기록을 모두 지울까요?")
                .setNegativeButton("취소", null)
                .setPositiveButton("초기화", (dialog, which) -> {
                    getPreferences(MODE_PRIVATE).edit().remove(key).apply();
                    render();
                    Toast.makeText(this, "관리 기록을 초기화했습니다.", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void updateProgress() {
        if (progressText == null) {
            return;
        }
        Set<String> completed = getPreferences(MODE_PRIVATE)
                .getStringSet(PREFERENCES + ":" + dateKey(), Collections.emptySet());
        progressText.setText(getString(
                R.string.checklist_progress, completed.size(), MAINTENANCE_ITEMS.size()));
        progressBar.setProgress(completed.size());
    }

    private Situation selectedSituation() {
        for (Situation situation : SITUATIONS) {
            if (situation.id.equals(selectedSituationId)) {
                return situation;
            }
        }
        return null;
    }

    private enum Screen {
        HOME,
        SCHEDULE,
        SITUATIONS,
        GUIDE
    }

    private static final class CareItem {
        final String title;
        final String description;

        CareItem(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }

    private static final class Situation {
        final String id;
        final String title;
        final List<CareItem> items;

        Situation(String id, String title, List<CareItem> items) {
            this.id = id;
            this.title = title;
            this.items = items;
        }
    }
}
