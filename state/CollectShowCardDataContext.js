import React, {createContext, useState} from 'react';

export const CollectShowCardDataContext = createContext({
  payload: {},
  hasPayload: () => {},
  updatePayload: newPayload => {},
  clearPayload: () => {},
});

function isEmpty(obj) {
  return Object.keys(obj).length === 0;
}

function CollectShowCardDataContextProvider({children}) {
  const [payloadData, setPayloadData] = useState({});

  function updatePayload(newPayload) {
    console.log('newPayload');
    console.log(newPayload);
    setPayloadData(currentState => newPayload);
  }

  function clearPayload() {
    setPayloadData(null);
  }

  function hasPayload() {
    return !isEmpty(payloadData);
  }

  const value = {
    payload: payloadData,
    hasPayload: hasPayload,
    updatePayload: updatePayload,
    clearPayload: clearPayload,
  };

  return (
    <CollectShowCardDataContext.Provider value={value}>
      {children}
    </CollectShowCardDataContext.Provider>
  );
}

export default CollectShowCardDataContextProvider;
