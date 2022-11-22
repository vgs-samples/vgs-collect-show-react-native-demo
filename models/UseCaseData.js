import {UseCases} from './UseCases';

class UseCaseData {
  constructor(routeName, title) {
    this.routeName = routeName;
    this.title = title;
  }
}

export const useCases = [
  new UseCaseData(UseCases.CollectCustomCardData, 'Collect Custom Card Data'),
  new UseCaseData(UseCases.CollectShowCardData, 'Collect Show Card Data'),
  new UseCaseData(UseCases.TokenizeCardData, 'Tokenize Card Data'),
];
