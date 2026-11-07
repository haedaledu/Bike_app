import { App } from "@capacitor/app";
import { Capacitor } from "@capacitor/core";

const maintenanceItems = [
  {
    title: "체인 청소 및 오일 도포",
    description: "오염 물질을 닦아내고 자전거 전용 오일을 바른 뒤, 체인 겉면에 남은 기름을 닦아냅니다.",
  },
  {
    title: "타이어 공기압 점검",
    description: "주행 전 타이어에 표시된 권장 공기압(PSI/BAR)을 확인하고 마모나 갈라짐이 없는지 살핍니다.",
  },
  {
    title: "브레이크 패드 및 장력 점검",
    description: "패드 마모 상태와 브레이크 레버 감도를 확인하고 와이어 또는 유압 브레이크가 정상적으로 작동하는지 점검합니다.",
  },
  {
    title: "변속기(드레일러) 세팅 점검",
    description: "변속할 때 체인이 튀거나 소음이 나지 않는지 확인하고, 필요한 경우 변속 케이블 장력을 조절합니다.",
  },
  {
    title: "구동계(체인링·스프라켓) 이물질 제거",
    description: "체인링과 스프라켓 톱니 사이에 낀 흙이나 이물질을 제거합니다.",
  },
  {
    title: "볼트 결합 상태 점검",
    description: "핸들바, 스템, 안장, 페달 등 주요 부위 볼트 풀림 방지 토크 점검",
  },
  {
    title: "프레임 닦기 및 균열 점검",
    description: "차체 표면 청소와 함께 프레임 균열(크랙) 발생 여부 외관 검사",
  },
  {
    title: "휠 트루잉(정렬) 점검",
    description: "휠이 좌우로 흔들리거나 정렬이 틀어졌는지 림과 스포크 상태 확인",
  },
];

const situations = {
  rain: {
    title: "자전거가 비에 젖었어요",
    items: [
      {
        title: "차체 및 구동계 물기 제거",
        description: "건조한 타월이나 천으로 프레임, 핸들바, 안장, 체인 및 톱니 부위의 빗물과 오염물(흙먼지)을 깨끗이 닦아냅니다.",
      },
      {
        title: "체인 완전 건조 및 녹 방지",
        description: "체인과 구동계 사이 사이에 남아 있는 물기를 닦고 충분히 말려 부식을 막습니다.",
      },
      {
        title: "체인 오일 재도포",
        description: "빗물에 씻겨 내려간 오일을 보충하도록 체인 마디마다 자전거 전용 오일을 바르고, 겉면에 남은 기름은 가볍게 닦아냅니다.",
      },
      {
        title: "베어링 부위 건조 및 점검",
        description: "헤드셋, BB(크랭크 축), 휠 허브 등 베어링 주변에 물기가 고이지 않았는지 확인하고, 바람이 잘 통하는 그늘에서 충분히 말립니다.",
      },
    ],
  },
  "before-ride": {
    title: "라이딩 전이에요",
    items: [
      {
        title: "타이어 공기압 및 표면 점검",
        description: "타이어를 눌러 적정 공기압(PSI)을 확인하고, 표면에 마모나 찢어짐, 이물질이 박혀 있는지 검사합니다.",
      },
      {
        title: "브레이크 제동력 확인",
        description: "앞뒤 브레이크 레버를 잡았을 때 유격이 적당한지 확인하고, 자전거를 살짝 밀어보며 패드가 꽉 잡히는지 점검합니다.",
      },
      {
        title: "구동계 및 체인 점검",
        description: "페달을 뒤로 돌려 체인 상태(건조함, 소음)를 살피고, 뒷바퀴를 들고 변속이 오차 없이 깔끔하게 이뤄지는지 체크합니다.",
      },
      {
        title: "결합부(휠·핸들·안장) 풀림 확인",
        description: "바퀴를 고정하는 QR 레버나 스루액슬이 꽉 조여졌는지, 핸들바와 안장이 흔들리지 않게 볼트가 단단히 고정되어 있는지 확인합니다.",
      },
    ],
  },
  fall: {
    title: "자전거가 넘어졌어요",
    items: [
      {
        title: "행어(변속기 고정 부위) 휘어짐 확인",
        description: "자전거가 넘어지면 뒷 변속기와 휠을 연결하는 행어가 가장 쉽게 휩니다. 변속기가 스포크(바퀴 살) 쪽으로 휘지 않았는지 외관을 점검합니다.",
      },
      {
        title: "변속 및 체인 간섭 테스트",
        description: "뒷바퀴를 들고 페달을 돌리며 변속을 진행합니다. 특히 가장 큰 톱니(저속 단수)로 변속할 때 변속기가 바퀴 살에 걸려 파손되지 않는지 확인합니다.",
      },
      {
        title: "핸들바 및 안장 정렬 점검",
        description: "넘어지는 충격으로 비틀어진 핸들바와 안장의 각도가 앞바퀴 및 프레임 직진 방향과 바르게 정렬되어 있는지 확인하고 조여줍니다.",
      },
      {
        title: "브레이크 및 프레임 충격/크랙 검사",
        description: "브레이크 레버 작동 유격과 제동력을 체크하고, 충격을 받은 프레임과 부품 표면에 균열(크랙)이나 파손이 없는지 살핍니다.",
      },
    ],
  },
};

const storageKey = "bike-maintenance-checklist-v1";
const screens = document.querySelectorAll(".screen");
const notice = document.querySelector("#notice");
const dateInput = document.querySelector("#schedule-date");
const maintenanceList = document.querySelector("#maintenance-list");
const resetButton = document.querySelector("#reset-checklist");
const guideTitle = document.querySelector("#guide-title");
const guideList = document.querySelector("#guide-list");
let completedByDate = {};
const navigationStack = ["home"];

function showNotice(message) {
  notice.textContent = message;
  notice.hidden = false;
}

function loadCompletedItems() {
  try {
    const saved = localStorage.getItem(storageKey);
    if (saved) {
      const parsed = JSON.parse(saved);
      if (parsed && typeof parsed === "object" && !Array.isArray(parsed)) {
        completedByDate = Object.fromEntries(
          Object.entries(parsed)
            .filter(([date, items]) => /^\d{4}-\d{2}-\d{2}$/.test(date) && Array.isArray(items))
            .map(([date, items]) => [
              date,
              [...new Set(items.filter((index) => Number.isInteger(index) && index >= 0 && index < maintenanceItems.length))],
            ]),
        );
      } else {
        showNotice("저장된 관리 일정 형식을 읽을 수 없습니다.");
      }
    }
  } catch {
    showNotice("저장된 관리 일정을 읽지 못했습니다.");
  }
}

function saveCompletedItems() {
  try {
    localStorage.setItem(storageKey, JSON.stringify(completedByDate));
    notice.hidden = true;
    return true;
  } catch {
    showNotice("체크 상태를 저장하지 못했습니다.");
    return false;
  }
}

function renderMaintenanceItems() {
  const date = dateInput.value;
  const completed = completedByDate[date] || [];
  maintenanceList.replaceChildren();

  maintenanceItems.forEach((item, index) => {
    const listItem = document.createElement("li");
    listItem.className = "checklist-item";
    if (completed.includes(index)) {
      listItem.classList.add("is-complete");
    }

    const label = document.createElement("label");
    label.className = "checklist-label";
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.checked = completed.includes(index);
    checkbox.setAttribute("aria-label", item.title);
    checkbox.addEventListener("change", () => {
      const updated = new Set(completedByDate[date] || []);
      if (checkbox.checked) {
        updated.add(index);
      } else {
        updated.delete(index);
      }
      completedByDate[date] = [...updated];
      listItem.classList.toggle("is-complete", checkbox.checked);
      saveCompletedItems();
    });

    const text = document.createElement("span");
    const title = document.createElement("span");
    title.className = "item-title";
    title.textContent = item.title;
    const description = document.createElement("span");
    description.className = "item-description";
    description.textContent = item.description;
    text.append(title, description);
    label.append(checkbox, text);
    listItem.append(label);
    maintenanceList.append(listItem);
  });
}

function renderGuide(situationKey) {
  const situation = situations[situationKey];
  if (!situation) {
    return;
  }

  guideTitle.textContent = situation.title;
  guideList.replaceChildren();
  situation.items.forEach((item) => {
    const listItem = document.createElement("li");
    listItem.className = "guide-item";
    const title = document.createElement("span");
    title.className = "item-title";
    title.textContent = item.title;
    const description = document.createElement("p");
    description.className = "item-description";
    description.textContent = item.description;
    listItem.append(title, description);
    guideList.append(listItem);
  });
}

function showScreen(screenName) {
  screens.forEach((screen) => {
    screen.hidden = screen.id !== `${screenName}-screen`;
  });
}

function navigateToScreen(screenName) {
  if (navigationStack[navigationStack.length - 1] !== screenName) {
    navigationStack.push(screenName);
  }
  showScreen(screenName);
}

function navigateBack() {
  if (navigationStack.length <= 1) {
    return false;
  }

  navigationStack.pop();
  showScreen(navigationStack[navigationStack.length - 1]);
  return true;
}

document.addEventListener("click", (event) => {
  const screenButton = event.target.closest("[data-screen]");
  if (screenButton) {
    navigateToScreen(screenButton.dataset.screen);
    return;
  }

  const situationButton = event.target.closest("[data-situation]");
  if (situationButton) {
    renderGuide(situationButton.dataset.situation);
    navigateToScreen("guide");
  }
});

dateInput.addEventListener("change", renderMaintenanceItems);

resetButton.addEventListener("click", () => {
  if (!completedByDate[dateInput.value]) {
    showNotice("초기화할 관리 기록이 없습니다.");
    return;
  }

  delete completedByDate[dateInput.value];
  const saved = saveCompletedItems();
  renderMaintenanceItems();
  if (saved) {
    showNotice("선택한 날짜의 관리 기록을 초기화했습니다.");
  }
});

if (Capacitor.isNativePlatform()) {
  App.addListener("backButton", () => {
    if (!navigateBack()) {
      App.exitApp();
    }
  });
}

const today = new Date();
dateInput.value = [
  today.getFullYear(),
  String(today.getMonth() + 1).padStart(2, "0"),
  String(today.getDate()).padStart(2, "0"),
].join("-");

loadCompletedItems();
renderMaintenanceItems();
