import React, {createContext, useState} from 'react';

export const CollectShowCardDataContext = createContext({
  payload: {},
  updatePayload: newPayload => {},
  clearPayload: () => {},
});

function CollectShowCardDataContextProvider({children}) {
  const [payload, setPayload] = useState({});

  function updatePayload(newPayload) {
    setPayload(newPayload);
  }

  function clearPayload() {
    setPayload(null);
  }

  const value = {
    payload: payload,
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
