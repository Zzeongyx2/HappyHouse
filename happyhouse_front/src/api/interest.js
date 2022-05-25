import { apiInstance, houseInstance } from "./index.js";

const api = apiInstance();
const house = houseInstance();

function saveRegion(region, success, fail) {
    console.log("===", region);
    api.post(`/map/region`, JSON.stringify(region)).then(success).catch(fail);
}

function getRegion(userid, success, fail) {
    console.log("===", userid);
    api.get(`/map/region/${userid}`).then(success).catch(fail);
}

function deleteRegion(region, success, fail) {
    api.post(`/map/region/r/`, JSON.stringify(region)).then(success).catch(fail);
}

export { saveRegion, getRegion, deleteRegion };
