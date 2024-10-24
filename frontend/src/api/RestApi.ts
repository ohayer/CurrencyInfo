const backendUrl = 'http://localhost:8080';

export const RestUrl = {
    getPersonSearchInfo: `${backendUrl}/currencies/requests`,
    getCurriencies: `${backendUrl}/currencies/list`,
    postWealthCalculator: `${backendUrl}/currencies/get-current-currency-value-command`
};