import {UseCases} from './UseCases';

class UseCaseData {
  constructor(routeName, title) {
    this.routeName = routeName;
    this.title = title;
  }
}

export const useCases = [
  new UseCaseData(
    UseCases.CollectShowCardDataSimple,
    'Collect Custom Card Data Simple Setup',
  ),
  new UseCaseData(
    UseCases.CollectShowCardDataAdvanced,
    'Collect Show Card Data Advances Setup',
  ),
];
