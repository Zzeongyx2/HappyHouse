import axios from "axios";
import { HAPPYHOUSE_BASE_URL, APT_DEAL_URL } from "@/config";
import { SUBSCRIPTION_BASE_URL } from "../config";

// axios 객체 생성
function apiInstance() {
  const instance = axios.create({
    baseURL: HAPPYHOUSE_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

function houseInstance() {
  const instance = axios.create({
    baseURL: APT_DEAL_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

function subscriptionIntance() {
  const instance = axios.create({
    baseURL: SUBSCRIPTION_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

export { apiInstance, houseInstance, subscriptionIntance };