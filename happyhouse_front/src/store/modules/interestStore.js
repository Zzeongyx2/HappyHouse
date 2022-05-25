import {
    saveRegion, getRegion, deleteRegion
} from "@/api/house.js";

const interestStore = {
    namespaced: true,
    CanSavable: false,
    state: {
        region: {
            userid: "",
            sido: "",
            gugun: "",
            dong: "",
        },
        regionList: [],
    },

    mutations: {
        SET_REGION: (state, region) => {
            if (region != null) {
                state.region = {
                    userid: "",
                    sido: "",
                    gugun: "",
                    dong: "",
                }
            } else {
                state.region = region;
            }
        },
        SET_CAN_SAVABLE: (state, CanSavable) => {
            state.CanSavable = CanSavable;
        },
    },

    actions: {
        async isDuplicateCheck({ commit }, region) {
            await regionCheck(
                region,
                (response) => {
                    console.log("지역 중복 검사 시도 성공");
                    if (response.data = "success") {
                        console.log("지역 저장 가능");
                        commit("SET_CAN_SAVABLE", true);
                    } else {
                        console.log("지역 저장 불가능");
                        commit("SET_CAN_SAVABLE", false);
                    }
                },
                () => { }
            );
        },
    }
}

export default interestStore;