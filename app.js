const maintenanceItems = [
  {
    title: "체인 청소 및 오일 도포",
    description: "오염 물질을 닦아내고 자전거 전용 루브(오일)를 도포한 뒤 겉면 남은 기름을 닦아내기",
  },
  {
    title: "타이어 공기압 점검",
    description: "주행 전 지정된 수치(PSI/BAR)에 맞게 공기 채우기 및 마모·크랙 상태 확인",
  },
  {
    title: "브레이크 패드 및 장력 점검",
    description: "패드 마모도 확인, 브레이크 레버 감도 및 와이어/유압 성능 체크",
  },
  {
    title: "변속기(드레일러) 세팅 점검",
    description: "변속 시 체인 튀임이나 잡음이 없는지 확인하고 와이어 장력 조절",
  },
  {
    title: "구동계(체인 ring, 스프라켓) 이물질 제거",
    description: "톱니 사이에 낀 흙 dust, 이물질 cleaning",
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
        description: "빗물에 씻겨 내려간 루브를 대신해 체인마디마다 자전거 전용 오일을 도포하고, 겉면에 남은 excess 오일은 가볍게 닦아냅니다.",
      },
      {
        title: "베어링 부위 건조 및 점검",
        description: "헤드셋, BB(크랭크 축), 휠 허브 등 베어링이 들어간 유입 부위에 물기가 고여 있지 않은지 확인하고 바람이 잘 통하는 그늘에서 바짝 말려줍니다.",
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
const guideTitle = document.querySelector("#guide-title");
const guideList = document.querySelector("#guide-list");
let completedByDate = {};

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
        completedByDate = parsed;
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
  } catch {
    showNotice("체크 상태를 저장하지 못했습니다.");
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

document.addEventListener("click", (event) => {
  const screenButton = event.target.closest("[data-screen]");
  if (screenButton) {
    showScreen(screenButton.dataset.screen);
    return;
  }

  const situationButton = event.target.closest("[data-situation]");
  if (situationButton) {
    renderGuide(situationButton.dataset.situation);
    showScreen("guide");
  }
});

dateInput.addEventListener("change", renderMaintenanceItems);

const today = new Date();
dateInput.value = [
  today.getFullYear(),
  String(today.getMonth() + 1).padStart(2, "0"),
  String(today.getDate()).padStart(2, "0"),
].join("-");

loadCompletedItems();
renderMaintenanceItems();
