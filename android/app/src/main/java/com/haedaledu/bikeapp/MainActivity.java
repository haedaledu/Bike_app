package com.haedaledu.bikeapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;

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
    private static final int GREEN = Color.rgb(25, 135, 84);
    private static final int TEXT = Color.rgb(24, 32, 28);
    private static final int MUTED = Color.rgb(94, 105, 98);
    private static final int BORDER = Color.rgb(222, 226, 222);
    private static final int BACKGROUND = Color.rgb(247, 248, 246);
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
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BACKGROUND);

        LinearLayout toolbar = new LinearLayout(this);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setPadding(dp(20), dp(12), dp(20), dp(12));
        if (currentScreen != Screen.HOME) {
            TextView back = text("‹", 34, TEXT, false);
            back.setGravity(Gravity.CENTER);
            back.setContentDescription("뒤로");
            toolbar.addView(back, new LinearLayout.LayoutParams(dp(42), dp(44)));
            back.setOnClickListener(view -> navigateBack());
        }
        TextView title = text(currentScreen == Screen.HOME ? "자전거 관리법" : screenTitle(), 18, TEXT, true);
        toolbar.addView(title, new LinearLayout.LayoutParams(0, -2, 1));
        root.addView(toolbar, new LinearLayout.LayoutParams(-1, -2));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(12), dp(20), dp(36));
        scroll.addView(content, new ScrollView.LayoutParams(-1, -2));
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
        addSpace(22);
        addText("안전하고 즐거운 라이딩을 위한", 16, MUTED, false);
        addText("자전거 관리법", 30, TEXT, true, 4);
        addText("간단한 점검으로 자전거를 오래, 안전하게 관리하세요.", 16, MUTED, false, 10);
        addSpace(24);
        addActionButton("날짜별 관리 일정", "점검 항목을 확인하고 완료 상태를 기록해요",
                () -> navigateTo(Screen.SCHEDULE));
        addActionButton("상황별 자전거 관리법", "비를 맞았거나 넘어졌을 때 필요한 점검을 확인해요",
                () -> navigateTo(Screen.SITUATIONS));
        addSpace(12);
        addText("체크 기록은 이 기기에 저장됩니다.", 13, MUTED, false);
    }

    private void renderSchedule() {
        addText("오늘의 점검", 26, TEXT, true);
        addText("날짜별로 완료한 관리 항목을 기록할 수 있어요.", 15, MUTED, false, 6);
        addSpace(18);

        Button dateButton = outlineButton(displayDate());
        content.addView(dateButton, matchWrap(48));
        dateButton.setOnClickListener(view -> showDatePicker());

        Button resetButton = outlineButton("이 날짜 기록 초기화");
        LinearLayout.LayoutParams resetParams = matchWrap(46);
        resetParams.topMargin = dp(10);
        content.addView(resetButton, resetParams);
        resetButton.setOnClickListener(view -> confirmReset());

        progressText = text("", 15, GREEN, true);
        LinearLayout.LayoutParams progressParams = matchWrap(-2);
        progressParams.topMargin = dp(20);
        progressParams.bottomMargin = dp(8);
        content.addView(progressText, progressParams);

        String storageKey = PREFERENCES + ":" + dateKey();
        for (int i = 0; i < MAINTENANCE_ITEMS.size(); i++) {
            addChecklistItem(MAINTENANCE_ITEMS.get(i), i, storageKey);
        }
        updateProgress();
    }

    private void renderSituations() {
        addText("어떤 상황인가요?", 26, TEXT, true);
        addText("상황에 맞는 점검 방법을 확인해 보세요.", 15, MUTED, false, 6);
        addSpace(18);
        for (Situation situation : SITUATIONS) {
            addActionButton(situation.title, "필요한 점검 항목 보기", () -> {
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
        addText(situation.title, 26, TEXT, true);
        addText("아래 항목을 차례대로 확인해 주세요.", 15, MUTED, false, 6);
        addSpace(18);
        for (CareItem item : situation.items) {
            addGuideItem(item);
        }
        addSpace(12);
        addText("손상이나 이상이 발견되면 안전을 위해 정비 전문가에게 점검을 받으세요.",
                14, MUTED, false);
    }

    private void addChecklistItem(CareItem item, int index, String storageKey) {
        LinearLayout card = card();
        CheckBox checkbox = new CheckBox(this);
        checkbox.setText(item.title);
        checkbox.setTextColor(TEXT);
        checkbox.setTextSize(17);
        checkbox.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        checkbox.setButtonTintList(new ColorStateList(
                new int[][]{new int[]{android.R.attr.state_checked}, new int[]{}},
                new int[]{GREEN, MUTED}));
        checkbox.setPadding(0, 0, 0, dp(4));

        Set<String> completed = getPreferences(MODE_PRIVATE).getStringSet(storageKey, Collections.emptySet());
        checkbox.setChecked(completed.contains(String.valueOf(index)));
        card.addView(checkbox, matchWrap(-2));
        TextView description = text(item.description, 14, MUTED, false);
        LinearLayout.LayoutParams descriptionParams = matchWrap(-2);
        descriptionParams.leftMargin = dp(38);
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
            updateProgress();
        });
    }

    private void addGuideItem(CareItem item) {
        LinearLayout card = card();
        card.addView(text(item.title, 17, TEXT, true), matchWrap(-2));
        TextView description = text(item.description, 15, MUTED, false);
        LinearLayout.LayoutParams descriptionParams = matchWrap(-2);
        descriptionParams.topMargin = dp(8);
        card.addView(description, descriptionParams);
        addCardToContent(card);
    }

    private void addActionButton(String label, String description, Runnable action) {
        Button button = new Button(this);
        button.setAllCaps(false);
        button.setText(getString(R.string.action_with_description, label, description));
        button.setTextColor(WHITE);
        button.setTextSize(16);
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        button.setPadding(dp(18), dp(12), dp(18), dp(12));
        button.setMinHeight(dp(74));
        button.setBackground(rounded(GREEN, GREEN, 10));
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
        button.setBackground(rounded(WHITE, BORDER, 8));
        return button;
    }

    private LinearLayout card() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(14), dp(16), dp(14));
        card.setBackground(rounded(WHITE, BORDER, 8));
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
        view.setLineSpacing(dp(2), 1f);
        if (bold) {
            view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
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
