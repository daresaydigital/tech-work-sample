export const CHANGE_PAGE = 'CHANGE_PAGE';

export default (page: string) => {
  return {
    type: CHANGE_PAGE,
    page
  };
};
