import { Moment } from 'moment';

export interface IRegnRevoSdmSuffix {
  id?: string;
  name?: string;
  descString?: string;
  orgInfo?: string;
  revokeTimeSpan?: number;
  revokeStart?: Moment;
  revokeOver?: Moment;
  punishOrg?: string;
  punishTime?: Moment;
  facts?: string;
  autoProcess?: boolean;
  revokeProof?: string;
  remarks?: string;
  punishPersonLastName?: string;
  punishPersonId?: string;
}

export class RegnRevoSdmSuffix implements IRegnRevoSdmSuffix {
  constructor(
    public id?: string,
    public name?: string,
    public descString?: string,
    public orgInfo?: string,
    public revokeTimeSpan?: number,
    public revokeStart?: Moment,
    public revokeOver?: Moment,
    public punishOrg?: string,
    public punishTime?: Moment,
    public facts?: string,
    public autoProcess?: boolean,
    public revokeProof?: string,
    public remarks?: string,
    public punishPersonLastName?: string,
    public punishPersonId?: string
  ) {
    this.autoProcess = this.autoProcess || false;
  }
}
