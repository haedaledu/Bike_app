"use strict";

const maintenanceItems = [
  ["체인 청소 및 오일 도포", "오염 물질을 닦아내고 자전거 전용 오일을 바른 뒤, 체인 겉면에 남은 기름을 닦아냅니다."],
  ["타이어 공기압 점검", "타이어에 표시된 권장 공기압(PSI/BAR)을 확인하고 마모나 갈라짐이 없는지 살핍니다."],
  ["브레이크 패드 및 장력 점검", "패드 마모 상태와 브레이크 레버 감도를 확인하고 브레이크가 정상 작동하는지 점검합니다."],
  ["변속기 세팅 점검", "변속할 때 체인이 튀거나 소음이 나지 않는지 확인하고 변속 상태를 살핍니다."],
  ["구동계 이물질 제거", "체인링과 스프라켓 톱니 사이에 낀 흙이나 이물질을 제거합니다."],
  ["볼트 결합 상태 점검", "핸들바, 스템, 안장, 페달 등 주요 부위의 볼트가 풀리지 않았는지 확인합니다."],
  ["프레임 닦기 및 균열 점검", "차체 표면을 닦고 프레임에 균열이나 손상이 없는지 살핍니다."],
  ["휠 정렬 점검", "휠이 좌우로 흔들리거나 정렬이 틀어지지 않았는지 림과 스포크 상태를 확인합니다."],
];

const situations = {
  rain: {
    title: "자전거가 비에 젖었어요", icon: "rain", color: "blue",
    items: [
      ["차체 및 구동계 물기 제거", "마른 타월이나 천으로 프레임, 안장, 체인과 톱니의 빗물 및 오염물을 닦아냅니다."],
      ["체인 완전 건조 및 녹 방지", "체인과 구동계 사이에 남은 물기를 닦고 충분히 말려 부식을 막습니다."],
      ["체인 오일 재도포", "체인 마디마다 자전거 전용 오일을 바르고 겉면에 남은 기름은 가볍게 닦아냅니다."],
      ["베어링 부위 건조 및 점검", "헤드셋, 크랭크 축, 휠 허브 주변에 물기가 고이지 않았는지 확인하고 충분히 말립니다."],
    ],
  },
  "before-ride": {
    title: "라이딩 전이에요", icon: "sun", color: "mint",
    items: [
      ["타이어 공기압 및 표면 점검", "권장 공기압을 확인하고 타이어 표면에 마모, 찢어짐, 이물질이 없는지 살핍니다."],
      ["브레이크 제동력 확인", "앞뒤 브레이크 레버를 잡아보고 자전거를 살짝 밀어 제동이 잘 되는지 확인합니다."],
      ["구동계 및 체인 점검", "체인이 지나치게 건조하거나 소음이 나지 않는지, 변속이 잘 되는지 확인합니다."],
      ["바퀴·핸들·안장 고정 확인", "바퀴 고정 장치와 핸들바, 안장이 흔들리지 않도록 단단히 고정됐는지 확인합니다."],
    ],
  },
  fall: {
    title: "자전거가 넘어졌어요", icon: "shield", color: "amber",
    items: [
      ["변속기 행어 휘어짐 확인", "변속기가 휠 쪽으로 휘거나 스포크에 닿지 않았는지 외관을 살핍니다."],
      ["변속 및 체인 간섭 점검", "변속기를 움직일 때 체인이나 변속기가 바퀴 살에 걸리지 않는지 확인합니다."],
      ["핸들바 및 안장 정렬 점검", "핸들바와 안장이 앞바퀴 및 프레임 방향과 바르게 정렬되어 있는지 확인합니다."],
      ["브레이크 및 프레임 손상 검사", "브레이크가 정상 작동하는지, 프레임과 부품 표면에 균열이나 파손이 없는지 살핍니다."],
    ],
  },
};

const storageKey = "bike-maintenance-checklist-v1";
const dateInput = document.querySelector("#schedule-date");
const list = document.querySelector("#maintenance-list");
const notice = document.querySelector("#notice");
const progressText = document.querySelector("#progress-text");
const progressBar = document.querySelector("#progress-bar");
const resetButton = document.querySelector("#reset-checklist");
let completedByDate = {};
let storageAvailable = true;

function showNotice(message) {
  notice.textContent = message;
  notice.hidden = false;
}

function loadCompletedItems() {
  try {
    const saved = localStorage.getItem(storageKey);
    if (saved === null) {
      completedByDate = {};
      return;
    }
    const parsed = JSON.parse(saved);
    if (!parsed || typeof parsed !== "object" || Array.isArray(parsed)
        || Object.entries(parsed).some(([date, items]) =>
          !/^\d{4}-\d{2}-\d{2}$/.test(date) || !Array.isArray(items)
          || items.some((item) => !Number.isInteger(item) || item < 0 || item >= maintenanceItems.length))) {
      throw new Error("Invalid checklist data");
    }
    completedByDate = parsed;
  } catch (error) {
    storageAvailable = false;
    showNotice("저장된 관리 기록을 읽을 수 없어 변경할 수 없습니다. 브라우저 저장 공간을 확인해 주세요.");
    console.error("Could not read checklist data:", error);
  }
}

function saveCompletedItems(next) {
  if (!storageAvailable) return false;
  try {
    localStorage.setItem(storageKey, JSON.stringify(next));
    completedByDate = next;
    notice.hidden = true;
    return true;
  } catch (error) {
    showNotice("관리 기록을 저장하지 못했습니다. 브라우저 저장 공간을 확인해 주세요.");
    console.error("Could not save checklist data:", error);
    return false;
  }
}

function renderMaintenanceItems() {
  const date = dateInput.value;
  const completed = completedByDate[date] || [];
  list.replaceChildren();
  resetButton.disabled = !date || !storageAvailable;
  progressText.textContent = `${new Set(completed).size} / ${maintenanceItems.length}개 완료`;
  progressBar.value = new Set(completed).size;

  maintenanceItems.forEach(([title, description], index) => {
    const item = document.createElement("li");
    item.className = "item-card";
    item.classList.toggle("is-complete", completed.includes(index));
    const label = document.createElement("label");
    label.className = "check-label";
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.checked = completed.includes(index);
    checkbox.disabled = !date || !storageAvailable;
    const titleText = document.createElement("span");
    titleText.textContent = title;
    const detail = document.createElement("p");
    detail.className = "item-description";
    detail.textContent = description;
    label.append(checkbox, titleText);
    item.append(label, detail);
    list.append(item);

    checkbox.addEventListener("change", () => {
      const updated = new Set(completedByDate[date] || []);
      if (checkbox.checked) updated.add(index);
      else updated.delete(index);
      const next = { ...completedByDate, [date]: [...updated] };
      saveCompletedItems(next);
      renderMaintenanceItems();
      const replacement = list.querySelectorAll("input")[index];
      if (replacement) replacement.focus({ preventScroll: true });
    });
  });
}

function renderGuide(key) {
  const situation = situations[key];
  if (!situation) return false;
  const summary = document.querySelector("#guide-summary");
  summary.className = `guide-summary ${situation.color}`;
  document.querySelector("#guide-title").textContent = situation.title;
  document.querySelector("#guide-icon use").setAttribute("href", `#icon-${situation.icon}`);
  const guideList = document.querySelector("#guide-list");
  guideList.replaceChildren();
  situation.items.forEach(([title, description], index) => {
    const item = document.createElement("li");
    item.className = "item-card";
    const heading = document.createElement("div");
    heading.className = "guide-heading";
    const number = document.createElement("span");
    number.className = "guide-number";
    number.textContent = String(index + 1).padStart(2, "0");
    number.setAttribute("aria-hidden", "true");
    const titleText = document.createElement("strong");
    titleText.textContent = title;
    const detail = document.createElement("p");
    detail.className = "item-description";
    detail.textContent = description;
    heading.append(number, titleText);
    item.append(heading, detail);
    guideList.append(item);
  });
  return true;
}

function renderRoute(moveFocus = false) {
  const route = location.hash.slice(1) || "home";
  const screen = route.startsWith("guide-")
    ? (renderGuide(route.slice(6)) ? "guide" : "home")
    : (["home", "schedule", "situations"].includes(route) ? route : "home");
  document.querySelectorAll(".screen").forEach((section) => {
    section.hidden = section.id !== screen;
  });
  if (moveFocus) {
    const heading = document.querySelector(`#${screen} h1`);
    if (heading) heading.focus({ preventScroll: true });
    window.scrollTo(0, 0);
  }
}

dateInput.addEventListener("change", renderMaintenanceItems);
resetButton.addEventListener("click", () => {
  const date = dateInput.value;
  if (!date || !(completedByDate[date] || []).length) {
    showNotice("초기화할 관리 기록이 없습니다.");
    return;
  }
  if (!window.confirm(`${date}의 완료 기록을 모두 지울까요?`)) return;
  const next = { ...completedByDate };
  delete next[date];
  if (saveCompletedItems(next)) {
    showNotice("선택한 날짜의 관리 기록을 초기화했습니다.");
    renderMaintenanceItems();
  }
});
window.addEventListener("hashchange", () => renderRoute(true));
window.addEventListener("storage", (event) => {
  if (event.key === storageKey || event.key === null) {
    storageAvailable = true;
    loadCompletedItems();
    renderMaintenanceItems();
  }
});

const today = new Date();
dateInput.value = [
  today.getFullYear(),
  String(today.getMonth() + 1).padStart(2, "0"),
  String(today.getDate()).padStart(2, "0"),
].join("-");
loadCompletedItems();
renderMaintenanceItems();
renderRoute();
