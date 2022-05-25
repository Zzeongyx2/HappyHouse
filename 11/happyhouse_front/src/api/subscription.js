import { subscriptionIntance } from "./index.js";

const sub = subscriptionIntance();

function subscriptionList(params, success, fail) {
    console.log(params);
    sub.get(`/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancDetail`, {params: params}).then(success).catch(fail);
}

export { subscriptionList };